package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.AttackGameController;

public class AttackOnDodgedWeaponAbilitiesCheckEvent extends AbstractGameEvent {
	
	public final PlayerCompleteServer source;
	public final PlayerCompleteServer target;
	public final AttackGameController controller;
	
	public AttackOnDodgedWeaponAbilitiesCheckEvent(
		PlayerCompleteServer source,
		PlayerCompleteServer target,
		AttackGameController controller
	) {
		this.source = source;
		this.target = target;
		this.controller = controller;
	}

}
