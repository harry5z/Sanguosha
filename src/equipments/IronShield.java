package equipments;

import basics.Attack;
import player.PlayerOriginalClientComplete;
import core.Card;


public class IronShield extends Shield
{

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
	public void onActivatedBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequiredToReact(Card card) 
	{
		if(card instanceof Attack && card.getColor() == Card.BLACK)
			return false;
		else
			return true;
	}
	
}
