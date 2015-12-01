package cards.specials.instant;

import player.PlayerComplete;
import player.PlayerSimple;

import commands.Command;
import commands.operations.special.FireAttackOperation;
import core.client.game.operations.Operation;

public class FireAttack extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3725224946954164125L;
	public static final String FIRE_ATTACK = "Fire Attack";
	public FireAttack(int num, Suit suit, int id)
	{
		super(num, suit, id);
	}

	@Override
	public String getName()
	{
		return FIRE_ATTACK;
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		for(PlayerSimple other : player.getOtherPlayersUI())
			if(other.getHandCount() != 0)// target must have card on hand
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		if(player.getHandCount() > 1) // self have card (other than fire attack) on hand
			player.getGameListener().setTargetSelectable(player.getPlayerInfo(), true);
		return new FireAttackOperation(player,this,next);
	}
}
