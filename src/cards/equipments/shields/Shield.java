package equipments.shields;

import update.Damage;
import core.Card;
import equipments.Equipment;

public abstract class Shield extends Equipment
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3973053122566006924L;

	public Shield(int num, int suit) 
	{
		super(num, suit, Equipment.SHIELD);
	}
	public abstract boolean mustReactTo(Card card);
	
	//public abstract boolean hasEffectOn(Update update);
	
	public abstract void modifyDamage(Damage damage);
}
