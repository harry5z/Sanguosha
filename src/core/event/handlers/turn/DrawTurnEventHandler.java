package core.event.handlers.turn;

import core.event.game.turn.DrawTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
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
	protected void handleIfActivated(DrawTurnEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		this.player.clearDisposalArea();
		if (this.player.equals(game.getCurrentPlayer())) {
			game.drawCards(this.player, 2);
		}
	}

}
