package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestShowCardEvent extends AbstractSingleTargetGameEvent {

	public RequestShowCardEvent(PlayerInfo target) {
		super(target);
	}

}
