package core.event.handlers.basic;

import commands.game.client.RequestAttackGameUIClientCommand;
import core.event.game.basic.RequestAttackEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestAttackEventHandler extends AbstractEventHandler<RequestAttackEvent> {

	public RequestAttackEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestAttackEvent> getEventClass() {
		return RequestAttackEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestAttackEvent event, Game game) throws GameFlowInterruptedException {
		game.getConnectionController().sendCommandToPlayer(
			this.player.getName(),
			new RequestAttackGameUIClientCommand(event.getTarget(), event.getMessage())
		);
	}

}
