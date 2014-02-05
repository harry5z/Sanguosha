package equipments.shields;

import update.Damage;
import core.Card;

public class TaichiFormation extends Shield
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6729114905115919334L;

	public TaichiFormation(int num, int suit) 
	{
		super(num, suit);
		
	}

	@Override
	public String getName() 
	{
		return "Taichi Formation";
	}

	@Override
	public boolean mustReactTo(Card card) 
	{
		return true;
	}

	@Override
	public void modifyDamage(Damage damage) {
		
	}
	
}
