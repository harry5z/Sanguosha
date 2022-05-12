package core.event.game.turn;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;

public class DealTurnEvent extends AbstractGameEvent {
	
	public final PlayerCompleteServer currentPlayer;
	
	public DealTurnEvent(PlayerCompleteServer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
