package equipments.shields;

import update.Damage;
import core.Card;

public class SilverLion extends Shield
{

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
	public boolean isRequiredToReactTo(Card card) 
	{
		return true;
	}

	@Override
	public void modifyDamage(Damage damage) {
		if(damage.getAmount() > 1)
			damage.setAmount(1);
	}


}
