package core.event.game;

import cards.equipments.Equipment.EquipmentType;
import core.player.PlayerCompleteServer;
import core.server.game.controllers.mechanics.UnequipGameController;

public class UnequipItemAbilityEvent extends AbstractGameEvent {

	public final PlayerCompleteServer player;
	public final EquipmentType equipmentType;
	public final UnequipGameController controller;
	
	public UnequipItemAbilityEvent(PlayerCompleteServer player, EquipmentType equipmentType, UnequipGameController controller) {
		this.player = player;
		this.equipmentType = equipmentType;
		this.controller = controller;
	}
}
