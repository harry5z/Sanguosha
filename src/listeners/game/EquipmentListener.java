package listeners.game;

import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;

public interface EquipmentListener 
{
	/**
	 * Require that equipment != null
	 * @param equipment
	 */
	public void onEquipped(Equipment equipment);
	/**
	 * un-equip an equipment, require: type = Equipment.SOMETYPE
	 * @param type
	 */
	public void onUnequipped(EquipmentType type);
}
