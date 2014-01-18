package equipments;

import core.Card;


public abstract class Shield extends Equipment
{

	public Shield(int num, int suit) 
	{
		super(num, suit, Equipment.SHIELD);
	}
	public abstract boolean isRequiredToReactTo(Card card);
}
