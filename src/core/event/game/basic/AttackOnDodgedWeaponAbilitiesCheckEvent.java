package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.AttackGameController;

public class AttackOnDodgedWeaponAbilitiesCheckEvent extends AbstractGameEvent {
	
	public final PlayerCompleteServer source;
	public final AttackGameController controller;
	
	public AttackOnDodgedWeaponAbilitiesCheckEvent(PlayerCompleteServer source, AttackGameController controller) {
		this.source = source;
		this.controller = controller;
	}

}
