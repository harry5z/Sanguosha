package core.event.game;

import core.event.game.basic.AbstractSingleTargetGameEvent;
import core.player.PlayerInfo;
import core.server.game.controllers.mechanics.DodgeGameController;

public class DodgeTargetEquipmentCheckEvent extends AbstractSingleTargetGameEvent {
	
	public final DodgeGameController controller;

	public DodgeTargetEquipmentCheckEvent(DodgeGameController controller, PlayerInfo target) {
		super(target);
		this.controller = controller;
	}

}
