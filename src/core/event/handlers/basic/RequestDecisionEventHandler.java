package core.event.handlers.basic;

import commands.game.client.DecisionUIClientCommand;
import core.event.game.basic.RequestDecisionEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import exceptions.server.game.GameFlowInterruptedException;

public class RequestDecisionEventHandler extends AbstractEventHandler<RequestDecisionEvent> {

	public RequestDecisionEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<RequestDecisionEvent> getEventClass() {
		return RequestDecisionEvent.class;
	}

	@Override
	protected void handleIfActivated(RequestDecisionEvent event, GameDriver game) throws GameFlowInterruptedException {
		game.getConnectionController().sendCommandToPlayer(
			this.player.getName(),
			new DecisionUIClientCommand(event.getTarget(), event.getMessage())
		);
	}

}
