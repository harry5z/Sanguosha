package core.event.handlers.turn;

import core.event.game.turn.DrawStartTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.mechanics.TurnGameController.TurnStage;
import exceptions.server.game.GameFlowInterruptedException;

public class SkipDrawTurnEventHandler extends AbstractEventHandler<DrawStartTurnEvent> {

	public SkipDrawTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DrawStartTurnEvent> getEventClass() {
		return DrawStartTurnEvent.class;
	}

	@Override
	protected void handleIfActivated(DrawStartTurnEvent event, Game game) throws GameFlowInterruptedException {
		if (this.player != game.getCurrentPlayer()) {
			return;
		}

		game.pushGameController(new AbstractSingleStageGameController(game) {
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				game.removeEventHandler(SkipDrawTurnEventHandler.this);
				// skip DRAW
				event.turn.setCurrentStage(TurnStage.DEAL_BEGINNING);				
			}
		});
	}

}
