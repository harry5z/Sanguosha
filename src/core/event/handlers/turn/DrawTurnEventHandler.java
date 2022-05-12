package core.event.handlers.turn;

import core.event.game.turn.DrawTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DrawTurnEventHandler extends AbstractEventHandler<DrawTurnEvent> {

	public DrawTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DrawTurnEvent> getEventClass() {
		return DrawTurnEvent.class;
	}

	@Override
	protected void handleIfActivated(DrawTurnEvent event, Game game) throws GameFlowInterruptedException {
		this.player.clearDisposalArea();
		if (this.player.equals(game.getCurrentPlayer())) {
			game.pushGameController(new AbstractSingleStageGameController() {
				@Override
				protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
					game.pushGameController(new ReceiveCardsGameController(player, game.getDeck().drawMany(2)));
				}
			});
		}
	}

}
