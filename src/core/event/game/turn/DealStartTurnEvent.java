package core.event.game.turn;

import core.server.game.controllers.TurnGameController;

public class DealStartTurnEvent extends AbstractTurnEvent {

	public DealStartTurnEvent(TurnGameController turn) {
		super(turn);
	}

}
