package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestShowCardEvent extends AbstractSingleTargetPlayerReactionEvent {

	public RequestShowCardEvent(PlayerInfo target, String message) {
		super(target, message);
	}

}
