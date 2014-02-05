package cards.equipments.shields;

import cards.Card;
import update.Damage;

public class SilverLion extends Shield
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4821532886423359596L;

	public SilverLion(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Silver Lion";
	}

	@Override
	public boolean mustReactTo(Card card) 
	{
		return true;
	}

	@Override
	public void modifyDamage(Damage damage) {
		if(damage.getAmount() > 1)
			damage.setAmount(1);
	}


}
