package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestDodgeEvent extends AbstractBasicGameEvent {

	public RequestDodgeEvent(PlayerInfo targetInfo) {
		super(targetInfo);
	}

}
