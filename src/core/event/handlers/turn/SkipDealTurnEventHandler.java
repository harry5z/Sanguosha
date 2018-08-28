package core.event.handlers.turn;

import core.event.game.turn.DealStartTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.TurnGameController.TurnStage;
import exceptions.server.game.GameFlowInterruptedException;

public class SkipDealTurnEventHandler extends AbstractEventHandler<DealStartTurnEvent> {

	public SkipDealTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DealStartTurnEvent> getEventClass() {
		return DealStartTurnEvent.class;
	}

	@Override
	protected void handleIfActivated(DealStartTurnEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (this.player != game.getCurrentPlayer()) {
			return;
		}

		throw new GameFlowInterruptedException(() -> {
			game.removeEventHandler(this);
			// skip DEAL
			event.turn.setCurrentStage(TurnStage.DISCARD_BEGINNING);
			game.getGameController().proceed();
		});
	}

}
