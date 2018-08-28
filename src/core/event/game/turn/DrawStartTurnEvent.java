package core.event.game.turn;

import core.server.game.controllers.TurnGameController;

public class DrawStartTurnEvent extends AbstractTurnEvent {

	public DrawStartTurnEvent(TurnGameController turn) {
		super(turn);
	}

}
