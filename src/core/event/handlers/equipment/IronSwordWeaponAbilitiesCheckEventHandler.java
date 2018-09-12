package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.basic.AttackOnLockWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class IronSwordWeaponAbilitiesCheckEventHandler extends AbstractEventHandler<AttackOnLockWeaponAbilitiesCheckEvent> {

	public IronSwordWeaponAbilitiesCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackOnLockWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackOnLockWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackOnLockWeaponAbilitiesCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.target.isEquipped(EquipmentType.SHIELD)) {
			// deactivate target shield abilities
			event.target.getShield().onDeactivated(game, event.target);
			game.registerEventHandler(new IronSwordTargetShieldResetEventHandler(this.player));
		}
		
	}

}
