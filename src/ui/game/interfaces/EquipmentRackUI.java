package ui.game.interfaces;

import java.util.Collection;
import java.util.function.Consumer;

import cards.equipments.Equipment.EquipmentType;

public interface EquipmentRackUI {
	
	public void setActivatable(Collection<EquipmentType> types, Consumer<EquipmentUI> consumer);
	
	public void setUnactivatable(Collection<EquipmentType> types);
	
	public void setActivated(Collection<EquipmentType> types, boolean activated);
	
}
