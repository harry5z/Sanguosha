package basics;


import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.AttackEvent;
import core.Basic;
import core.Element;
import core.Operation;


public class Attack extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4346640648436436523L;
	private Element element;//Normal, Fire, Thunder
	
	public static final String ATTACK = "Attack";
	public static final String FIRE_ATTACK = "Attack(Fire)";
	public static final String THUNDER_ATTACK = "Attack(Thunder)";
	

	public Attack(Element e, int num, int suit)
	{
		super(num, suit);
		element = e;
	}
	public Element getElement()
	{
		return element;
	}
	@Override
	public String getName()
	{
		if(element == Element.NORMAL)
			return ATTACK;
		else if(element == Element.FIRE)
			return FIRE_ATTACK;
		else
			return THUNDER_ATTACK;
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player)
	{
		return player.getAttackUsed() < player.getAttackLimit();
	}
	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) 
	{
		player.setCancelEnabled(true);//can cancel
		player.setCardOnHandSelected(this, true);
		return new AttackEvent(player,this,next);
	}

}
