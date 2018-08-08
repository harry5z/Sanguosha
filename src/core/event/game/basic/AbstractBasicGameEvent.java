package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerInfo;

public class AbstractBasicGameEvent extends AbstractGameEvent {

	private PlayerInfo targetInfo;
	
	public AbstractBasicGameEvent(PlayerInfo targetInfo) {
		this.targetInfo = targetInfo;
	}
	
	public PlayerInfo getTargetInfo() {
		return this.targetInfo;
	}
}
