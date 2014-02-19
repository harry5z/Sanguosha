package cards.specials.instant;

import player.PlayerClientComplete;
import player.PlayerClientSimple;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.FireAttackOperation;

public class FireAttack extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725224946954164125L;
	public static final String FIRE_ATTACK = "Fire Attack";
	public FireAttack(int num, Suit suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return FIRE_ATTACK;
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player,Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		for(PlayerClientSimple other : player.getOtherPlayers())
			if(other.getCardsOnHandCount() != 0)//target must have card on hand
				player.setTargetSelectable(other.getPlayerInfo(), true);
		if(player.getCardsOnHandCount() > 1)
			player.setTargetSelectable(player.getPlayerInfo(), true);
		return new FireAttackOperation(player,this,next);
	}
}
