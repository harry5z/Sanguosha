package equipments;

import core.Card;
import core.Equipment;


public abstract class Shield extends Equipment
{

	public Shield(int num, int suit) 
	{
		super(num, suit, Equipment.SHIELD);
	}
	public abstract boolean isRequiredToReact(Card card);
}
