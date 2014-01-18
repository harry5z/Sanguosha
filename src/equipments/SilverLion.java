package equipments;

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


}
