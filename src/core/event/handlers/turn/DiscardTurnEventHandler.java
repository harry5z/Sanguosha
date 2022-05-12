package core.event.handlers.turn;

import core.event.game.turn.DiscardTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import exceptions.server.game.GameFlowInterruptedException;

public class DiscardTurnEventHandler extends AbstractEventHandler<DiscardTurnEvent> {

	public DiscardTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DiscardTurnEvent> getEventClass() {
		return DiscardTurnEvent.class;
	}

	@Override
	protected void handleIfActivated(DiscardTurnEvent event, GameDriver game) throws GameFlowInterruptedException {
		player.clearDisposalArea();
	}

}
