package core.event.game.dodge;

import core.event.game.AbstractGameEvent;
import core.player.PlayerInfo;

public class RequestDodgeEvent extends AbstractGameEvent {

	private PlayerInfo targetInfo;
	
	public RequestDodgeEvent(PlayerInfo targetInfo) {
		this.targetInfo = targetInfo;
	}
	
	public PlayerInfo getTargetInfo() {
		return this.targetInfo;
	}
}
