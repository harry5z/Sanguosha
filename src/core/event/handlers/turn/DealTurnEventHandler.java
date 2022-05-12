package core.event.handlers.turn;

import core.event.game.turn.DealTurnEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import exceptions.server.game.GameFlowInterruptedException;

public class DealTurnEventHandler extends AbstractEventHandler<DealTurnEvent> {
	
	public DealTurnEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public void handleIfActivated(DealTurnEvent event, GameDriver game) throws GameFlowInterruptedException {
		player.clearDisposalArea();
	}

	@Override
	public Class<DealTurnEvent> getEventClass() {
		return DealTurnEvent.class;
	}

}
