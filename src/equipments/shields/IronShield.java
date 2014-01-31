package equipments.shields;

import update.Damage;
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
	public boolean mustReactTo(Card card) 
	{
		return !(card instanceof Attack && card.getColor() == Card.BLACK);
	}

	@Override
	public void modifyDamage(Damage damage) {
		
	}

	
}
