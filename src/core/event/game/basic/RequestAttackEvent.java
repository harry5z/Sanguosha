package core.event.game.basic;

import core.player.PlayerInfo;

public class RequestAttackEvent extends AbstractBasicGameEvent {

	public RequestAttackEvent(PlayerInfo targetInfo) {
		super(targetInfo);
	}

}
