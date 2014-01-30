package equipments.shields;

import basics.Attack;
import specials.ArrowSalvo;
import specials.BarbarianInvasion;
import update.Damage;
import core.Card;
import core.Element;

public class RattanArmor extends Shield
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4377220192386216241L;

	public RattanArmor(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Rattan Armor";
	}

	@Override
	public boolean isRequiredToReactTo(Card card)
	{
		if(card instanceof ArrowSalvo || card instanceof BarbarianInvasion)
			return false;
		else if(card instanceof Attack && ((Attack)card).getElement() == Element.NORMAL)
			return false;
		else
			return true;
	}

	@Override
	public void modifyDamage(Damage damage) 
	{
		if(damage.getElement() == Element.FIRE)
			damage.setAmount(damage.getAmount()+1);
	}

	
}
