package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;

public class AttackEndEvent extends AbstractGameEvent {

	public final PlayerCompleteServer source;
	public final PlayerCompleteServer target;
	
	public AttackEndEvent(PlayerCompleteServer source, PlayerCompleteServer target) {
		this.source = source;
		this.target = target;
	}
}
