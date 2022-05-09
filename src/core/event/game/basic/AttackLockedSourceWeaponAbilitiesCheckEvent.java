package core.event.game.basic;

import core.event.game.AbstractGameEvent;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.mechanics.AttackResolutionGameController;

public class AttackLockedSourceWeaponAbilitiesCheckEvent extends AbstractGameEvent {

	public final PlayerCompleteServer source;
	public final PlayerCompleteServer target;
	public final AttackResolutionGameController controller;
	
	public AttackLockedSourceWeaponAbilitiesCheckEvent(PlayerCompleteServer source, PlayerCompleteServer target, AttackResolutionGameController controller) {
		this.source = source;
		this.target = target;
		this.controller = controller;
	}
}
