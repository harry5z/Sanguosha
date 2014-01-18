package equipments;

import basics.Attack;
import core.Card;

public class IronShield extends Shield
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4370802087723597065L;

	public IronShield(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Iron Shield";
	}

	@Override
	public boolean isRequiredToReactTo(Card card) 
	{
		if(card instanceof Attack && card.getColor() == Card.BLACK)
			return false;
		else
			return true;
	}

	
}
