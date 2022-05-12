package core.event.handlers.equipment;

import core.event.game.basic.AttackPreDamageWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import core.server.game.controllers.equipment.IcySwordGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class IcySwordAbilityCheckEventHandler extends AbstractEventHandler<AttackPreDamageWeaponAbilitiesCheckEvent> {

	public IcySwordAbilityCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackPreDamageWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackPreDamageWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackPreDamageWeaponAbilitiesCheckEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.target.getHandCount() == 0 && !event.target.isEquipped()) {
			// cannot use Icy Sword if target does not have cards on hand or equipment
			return;
		}
		
		game.pushGameController(new IcySwordGameController(event.source, event.target, event.controller));
	}

}
