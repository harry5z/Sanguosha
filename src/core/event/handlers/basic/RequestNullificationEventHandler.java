package core.event.handlers.basic;

import commands.game.client.RequestNullificationGameUIClientCommand;
import core.event.game.basic.RequestNullificationEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestNullificationEventHandler extends AbstractEventHandler<RequestNullificationEvent> {

	public RequestNullificationEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestNullificationEvent> getEventClass() {
		return RequestNullificationEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestNullificationEvent event, Game game) throws GameFlowInterruptedException {
		game.getConnectionController().sendCommandToPlayer(this.player.getName(), new RequestNullificationGameUIClientCommand(event.getMessage()));
	}

}
