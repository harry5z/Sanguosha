package cards.equipments.shields;

import commands.Damage;
import commands.Damage.Element;
import cards.Card;
import cards.basics.Attack;
import cards.specials.instant.ArrowSalvo;
import cards.specials.instant.BarbarianInvasion;

public class RattanArmor extends Shield
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4377220192386216241L;

	public RattanArmor(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Rattan Armor";
	}

	@Override
	public boolean mustReactTo(Card card)
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
