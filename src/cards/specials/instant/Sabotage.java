package cards.specials.instant;

import player.PlayerComplete;
import player.PlayerSimple;

import commands.Command;
import commands.operations.Operation;
import commands.operations.special.SabotageOperation;

public class Sabotage extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2917645089871877494L;

	public Sabotage(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Sabotage";
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		for(PlayerSimple other : player.getOtherPlayers())
			if(other.getHandCount() != 0 || other.isEquipped())//target must have something
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		return new SabotageOperation(player.getPlayerInfo(),this,next);
	}
}
