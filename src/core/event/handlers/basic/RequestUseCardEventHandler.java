package core.event.handlers.basic;

import commands.game.client.RequestUseCardGameUIClientCommand;
import core.event.game.basic.RequestUseCardEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestUseCardEventHandler extends AbstractEventHandler<RequestUseCardEvent> {

	public RequestUseCardEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestUseCardEvent> getEventClass() {
		return RequestUseCardEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestUseCardEvent event, Game game) throws GameFlowInterruptedException {
		game.getConnectionController().sendCommandToPlayer(
			this.player.getName(),
			new RequestUseCardGameUIClientCommand(event.getTarget(), event.getMessage(), event.getPredicates())
		);
	}

}
