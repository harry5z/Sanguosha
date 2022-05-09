package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.mechanics.AttackGameController;

public class AttackPreAcquisitionWeaponAbilitiesCheckEvent extends AbstractGameEvent {
	
	public final PlayerCompleteServer source;
	public final AttackGameController controller;
	
	public AttackPreAcquisitionWeaponAbilitiesCheckEvent(PlayerCompleteServer source, AttackGameController controller) {
		this.source = source;
		this.controller = controller;
	}

}
