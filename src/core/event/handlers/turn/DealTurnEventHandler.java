package core.event.handlers.turn;

import commands.game.client.DealStartGameUIClientCommmand;
import core.event.game.turn.DealTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class DealTurnEventHandler extends AbstractEventHandler<DealTurnEvent> {
	
	public DealTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public void handleIfActivated(DealTurnEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		connection.sendCommandToPlayer(player.getName(), new DealStartGameUIClientCommmand(game.getCurrentPlayer().getPlayerInfo()));
	}

	@Override
	public Class<DealTurnEvent> getEventClass() {
		return DealTurnEvent.class;
	}

}
