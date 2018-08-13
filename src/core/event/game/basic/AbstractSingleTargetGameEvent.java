package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerInfo;

public class AbstractSingleTargetGameEvent extends AbstractGameEvent {

	private PlayerInfo target;
	
	public AbstractSingleTargetGameEvent(PlayerInfo target) {
		this.target = target;
	}
	
	public PlayerInfo getTarget() {
		return this.target;
	}
}
