package cards.basics;


import player.PlayerClientComplete;
import update.Damage.Element;
import update.Update;
import update.operations.AttackOperation;
import update.operations.Operation;


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
	

	public Attack(Element e, int num, Suit suit)
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
	public boolean isActivatableBy(PlayerClientComplete player)
	{
		return player.getAttackUsed() < player.getAttackLimit();
	}
	@Override
	public Operation onActivatedBy(PlayerClientComplete player,Update next) 
	{
		player.setCancelEnabled(true);//can cancel
		player.setCardOnHandSelected(this, true);
		return new AttackOperation(player,this,next);
	}

}
