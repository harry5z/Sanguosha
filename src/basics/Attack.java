package basics;

import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Basic;
import core.Player;


public class Attack extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8978199242282700562L;
	private int element = 0;//0.Normal 1.Fire 2.Thunder
	public static final int NORMAL = 0;
	public static final int FIRE = 1;
	public static final int THUNDER = 2;
	
	public static final String ATTACK = "Attack";
	public static final String FIRE_ATTACK = "Fire Attack";
	public static final String THUNDER_ATTACK = "Thunder Attack";
	
	private Player target;
	private PlayerOriginalClientComplete source;
	public Attack(int e, int num, int suit)
	{
		super(num, suit);
		target = null;
		source = null;
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
		source = player;
		source.getUpdateStack().push(this);
		for(PlayerOriginalClientSimple p : source.getOtherPlayers())
			if(source.isPlayerInRange(p,source.getNumberOfPlayersAlive()))
				source.setTargetSelectable(p, true);
	}
	@Override
	public void onDeactivatedBy(PlayerOriginalClientComplete player)
	{
		super.onDeactivatedBy(player);
		for(PlayerOriginalClientSimple p : player.getOtherPlayers())
			player.setTargetSelectable(p, false);
	}
	@Override
	public void playerOperation(PlayerOriginalClientComplete player) 
	{
		if(player.equals(source))
			player.setAttackUsed(player.getAttackUsed()+1);//use an attack
		else if(player.equals(target))
		{
			player.setCardSelectableByName(Dodge.DODGE, true);
			player.setCancelEnabled(true);
		}
		
	}
	@Override
	public void onPlayerSelected(PlayerOriginalClientSimple player) 
	{
		if(target == null)
		{
			target = player;
			source.selectTarget(player);
			source.setConfirmEnabled(true);
			source.setCancelEnabled(true);
		}
		else if(target.equals(player))
		{
			source.unselectTarget(target);
			target = null;
			source.setConfirmEnabled(false);
		}
		else
		{
			source.unselectTarget(target);
			target = player;
			source.selectTarget(player);
		}
	}
}
