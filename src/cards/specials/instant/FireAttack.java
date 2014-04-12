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
	protected Operation createOperation(PlayerClientComplete player, Update next) 
	{
		for(PlayerClientSimple other : player.getOtherPlayers())
			if(other.getCardsOnHandCount() != 0)// target must have card on hand
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		if(player.getCardsOnHandCount() > 1) // self have card (other than fire attack) on hand
			player.getGameListener().setTargetSelectable(player.getPlayerInfo(), true);
		return new FireAttackOperation(player,this,next);
	}
}
