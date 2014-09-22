package cards.specials.instant;

import commands.Command;
import commands.operations.Operation;
import commands.operations.special.FireAttackOperation;
import player.PlayerComplete;
import player.PlayerSimple;

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
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		for(PlayerSimple other : player.getOtherPlayers())
			if(other.getHandCount() != 0)// target must have card on hand
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		if(player.getHandCount() > 1) // self have card (other than fire attack) on hand
			player.getGameListener().setTargetSelectable(player.getPlayerInfo(), true);
		return new FireAttackOperation(player,this,next);
	}
}
