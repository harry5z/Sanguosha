package core.event.handlers.turn;

import core.event.game.turn.EndTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import exceptions.server.game.GameFlowInterruptedException;

public class DefaultEndTurnEventHandler extends AbstractEventHandler<EndTurnEvent> {

	public DefaultEndTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<EndTurnEvent> getEventClass() {
		return EndTurnEvent.class;
	}

	@Override
	protected void handleIfActivated(EndTurnEvent event, GameDriver game) throws GameFlowInterruptedException {
		this.player.resetPlayerStates();
	}

}
