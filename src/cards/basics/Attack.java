package cards.basics;


import commands.Command;
import commands.Damage.Element;
import commands.operations.AttackOperation;
import commands.operations.Operation;
import player.PlayerComplete;


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
	public boolean isActivatableBy(PlayerComplete player)
	{
		return player.getAttackUsed() < player.getAttackLimit();
	}
	@Override
	public Operation onActivatedBy(PlayerComplete player,Command next) 
	{
		player.setCancelEnabled(true);//can cancel
		player.setCardOnHandSelected(this, true);
		return new AttackOperation(player,this,next);
	}

}
