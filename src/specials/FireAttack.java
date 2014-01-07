package specials;

import player.PlayerOriginalClientComplete;
import player.PlayerOriginalClientSimple;
import core.Operation;
import core.Update;
import events.special_events.FireAttackOperation;

public class FireAttack extends Instant
{
	public static final String FIRE_ATTACK = "Fire_Attack";
	public FireAttack(int num, int suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return FIRE_ATTACK;
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		for(PlayerOriginalClientSimple other : player.getOtherPlayers())
			if(other.getCardsOnHandCount() != 0)//target must have card on hand
				player.setTargetSelectable(other.getPlayerInfo(), true);
		if(player.getCardsOnHandCount() > 1)
			player.setTargetSelectable(player.getPlayerInfo(), true);
		return new FireAttackOperation(player,this,next);
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return true;
	}

}
