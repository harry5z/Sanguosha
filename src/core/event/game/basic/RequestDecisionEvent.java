package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestDecisionEvent extends AbstractSingleTargetPlayerReactionEvent {

	public RequestDecisionEvent(PlayerInfo target, String message) {
		super(target, message);
	}

}
