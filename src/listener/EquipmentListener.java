package listener;

import core.Equipment;

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
	public void onUnequipped(int type);
}
