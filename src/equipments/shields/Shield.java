package equipments.shields;

import update.Damage;
import core.Card;
import equipments.Equipment;


public abstract class Shield extends Equipment
{

	public Shield(int num, int suit) 
	{
		super(num, suit, Equipment.SHIELD);
	}
	public abstract boolean isRequiredToReactTo(Card card);
	
	public abstract void modifyDamage(Damage damage);
}
