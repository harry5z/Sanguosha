package equipments;

import basics.Attack;
import player.PlayerOriginalClientComplete;
import specials.ArrowSalvo;
import specials.BarbarianInvasion;
import core.Card;

public class RattenArmor extends Shield
{
	public RattenArmor(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Ratten Armor";
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
		if(card instanceof ArrowSalvo || card instanceof BarbarianInvasion)
			return false;
		else if(card instanceof Attack && ((Attack)card).getElement() == Attack.NORMAL)
			return false;
		else
			return true;
	}

	
}
