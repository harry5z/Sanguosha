package core.server.game.controllers.interfaces;

import cards.equipments.Equipment;
import core.server.game.controllers.GameController;

public interface EquipmentUsableGameController extends GameController {

	public void onEquipped(Equipment equipment);
	
}
