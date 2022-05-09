package core.server.game.controllers;

import cards.equipments.Equipment;

public interface EquipmentUsableGameController extends GameController {

	public void onEquipped(Equipment equipment);
	
}
