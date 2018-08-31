package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestAttackEvent extends AbstractSingleTargetPlayerReactionEvent {
	
	public RequestAttackEvent(PlayerInfo targetInfo, String message) {
		super(targetInfo, message);
	}

}
