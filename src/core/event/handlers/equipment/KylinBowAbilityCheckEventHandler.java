package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.basic.AttackPreDamageWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.equipment.KylinBowGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class KylinBowAbilityCheckEventHandler extends AbstractEventHandler<AttackPreDamageWeaponAbilitiesCheckEvent> {

	public KylinBowAbilityCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackPreDamageWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackPreDamageWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackPreDamageWeaponAbilitiesCheckEvent event, Game game)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (!event.target.isEquipped(EquipmentType.HORSEPLUS) && !event.target.isEquipped(EquipmentType.HORSEMINUS)) {
			return;
		}
		
		game.pushGameController(new KylinBowGameController(game, event.source, event.target));
	}

}
