package core.event.game;

import core.event.game.basic.AbstractSingleTargetGameEvent;
import core.player.PlayerInfo;

public class DodgeArbitrationEvent extends AbstractSingleTargetGameEvent {

	public DodgeArbitrationEvent(PlayerInfo target) {
		super(target);
	}

}
