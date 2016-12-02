package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.player.PlayerCompleteServer;

public abstract class AbstractShieldUnequipEventHandler extends AbstractUnequipEventHandler {

	public AbstractShieldUnequipEventHandler(PlayerCompleteServer player) {
		super(player, EquipmentType.SHIELD);
	}

}
