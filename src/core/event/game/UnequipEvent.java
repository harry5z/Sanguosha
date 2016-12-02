package core.event.game;

import cards.equipments.Equipment.EquipmentType;
import core.player.PlayerCompleteServer;

public class UnequipEvent extends AbstractGameEvent {
	
	public final PlayerCompleteServer player;
	public final EquipmentType equipmentType;
	
	public UnequipEvent(PlayerCompleteServer player, EquipmentType equipmentType) {
		this.player = player;
		this.equipmentType = equipmentType;
	}

}
