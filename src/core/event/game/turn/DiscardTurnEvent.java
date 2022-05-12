package core.event.game.turn;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;

public class DiscardTurnEvent extends AbstractGameEvent {

	public final PlayerCompleteServer currentPlayer;
	
	public DiscardTurnEvent(PlayerCompleteServer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
}
