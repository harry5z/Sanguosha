package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestDodgeEvent extends AbstractSingleTargetGameEvent {

	public RequestDodgeEvent(PlayerInfo targetInfo) {
		super(targetInfo);
	}

}
