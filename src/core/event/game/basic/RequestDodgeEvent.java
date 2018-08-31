package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestDodgeEvent extends AbstractSingleTargetPlayerReactionEvent {

	public RequestDodgeEvent(PlayerInfo targetInfo, String message) {
		super(targetInfo, message);
	}

}
