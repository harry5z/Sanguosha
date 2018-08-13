package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestAttackEvent extends AbstractSingleTargetGameEvent {

	public RequestAttackEvent(PlayerInfo targetInfo) {
		super(targetInfo);
	}

}
