package basics;

import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Basic;
import events.AttackEvent;


public class Attack extends Basic
{
	private int element = 0;//0.Normal 1.Fire 2.Thunder
	public static final int NORMAL = 0;
	public static final int FIRE = 1;
	public static final int THUNDER = 2;
	
	public static final String ATTACK = "Attack";
	public static final String FIRE_ATTACK = "Fire Attack";
	public static final String THUNDER_ATTACK = "Thunder Attack";
	

	public Attack(int e, int num, int suit)
	{
		super(num, suit);
		element = e;
	}
	public int getElement()
	{
		return element;
	}
	@Override
	public String getName()
	{
		if(element == NORMAL)
			return ATTACK;
		else if(element == FIRE)
			return FIRE_ATTACK;
		else
			return THUNDER_ATTACK;
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player)
	{
		if(player.getAttackUsed() < player.getAttackLimit())
			return true;
		else
			return false;
	}
	@Override
	public void onActivatedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(new AttackEvent(player,this));//operation set
		player.setCardOnHandSelected(this, true);//card is selected
		player.setCancelEnabled(true);//can cancel
	}

}
