package core.event.handlers.basic;

import commands.game.client.RequestShowCardGameUIClientCommand;
import core.event.game.basic.RequestShowCardEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestShowCardEventHandler extends AbstractEventHandler<RequestShowCardEvent> {

	public RequestShowCardEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestShowCardEvent> getEventClass() {
		return RequestShowCardEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestShowCardEvent event, Game game) throws GameFlowInterruptedException {
		game.getConnectionController().sendCommandToPlayer(this.player.getName(), new RequestShowCardGameUIClientCommand(event.getTarget(), event.getMessage()));
	}

}
