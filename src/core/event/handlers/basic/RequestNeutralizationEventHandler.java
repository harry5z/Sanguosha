package core.event.handlers.basic;

import commands.game.client.RequestNeutralizationGameUIClientCommand;
import core.event.game.basic.RequestNeutralizationEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestNeutralizationEventHandler extends AbstractEventHandler<RequestNeutralizationEvent> {

	public RequestNeutralizationEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestNeutralizationEvent> getEventClass() {
		return RequestNeutralizationEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestNeutralizationEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		connection.sendCommandToPlayer(this.player.getName(), new RequestNeutralizationGameUIClientCommand(event.getMessage()));
	}

}
