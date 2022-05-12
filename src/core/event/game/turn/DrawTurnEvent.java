package core.event.game.turn;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;

public class DrawTurnEvent extends AbstractGameEvent {
	
	public final PlayerCompleteServer currentPlayer;
	
	public DrawTurnEvent(PlayerCompleteServer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
