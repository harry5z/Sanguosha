package core.event.game;

import core.event.game.basic.AbstractSingleTargetGameEvent;
import core.player.PlayerInfo;

public class DodgeTargetEquipmentCheckEvent extends AbstractSingleTargetGameEvent {

	public DodgeTargetEquipmentCheckEvent(PlayerInfo target) {
		super(target);
	}

}
