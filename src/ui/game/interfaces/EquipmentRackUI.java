package ui.game.interfaces;

import java.awt.event.ActionListener;
import java.util.Collection;

import cards.equipments.Equipment.EquipmentType;

public interface EquipmentRackUI {
	
	public void setActivatable(Collection<EquipmentType> types, boolean activatable);
	
	public void setActivated(Collection<EquipmentType> types, boolean activated);
	
	public void registerOnActivatedListener(EquipmentType type, ActionListener listener);
	
	public void removeOnActivatedListeners(EquipmentType type);

}
