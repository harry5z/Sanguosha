package cards.equipments.shields;

import cards.Card;
import cards.equipments.Equipment;
import core.server.game.Damage;

public abstract class Shield extends Equipment {

	private static final long serialVersionUID = 3973053122566006924L;

	public Shield(int num, Suit suit, int id) {
		super(num, suit, EquipmentType.SHIELD, id);
	}

	public abstract boolean mustReactTo(Card card);

	public abstract void modifyDamage(Damage damage);

}
