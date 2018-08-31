package core.event.handlers.basic;

import commands.game.client.RequestDodgeGameUIClientCommand;
import core.event.game.basic.RequestDodgeEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestDodgeEventHandler extends AbstractEventHandler<RequestDodgeEvent> {

	public RequestDodgeEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestDodgeEvent> getEventClass() {
		return RequestDodgeEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestDodgeEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		connection.sendCommandToPlayer(
			this.player.getName(),
			new RequestDodgeGameUIClientCommand(event.getTarget(), event.getMessage())
		);
	}

}
