package ui.game.interfaces;

import cards.Card;
import cards.equipments.Equipment;

public interface EquipmentUI extends CardUI {

	public Equipment getEquipment();
	
	@Override
	default Card getCard() {
		return getEquipment();
	}
	
}
