package cards.equipments.shields;

import cards.equipments.Equipment;

public abstract class Shield extends Equipment {

	private static final long serialVersionUID = 3973053122566006924L;

	public Shield(int num, Suit suit, int id) {
		super(num, suit, EquipmentType.SHIELD, id);
	}

}
