package core.event.game.turn;

import core.event.game.AbstractGameEvent;
import core.server.game.controllers.mechanics.TurnGameController;

public class AbstractTurnEvent extends AbstractGameEvent {
	
	public final TurnGameController turn;
	
	public AbstractTurnEvent(TurnGameController turn) {
		this.turn = turn;
	}
	
}
