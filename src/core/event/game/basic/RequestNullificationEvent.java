package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestNullificationEvent extends AbstractSingleTargetPlayerReactionEvent {

	public RequestNullificationEvent(PlayerInfo target, String message) {
		super(target, message);
	}

}
