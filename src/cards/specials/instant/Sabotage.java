package cards.specials.instant;

import commands.Command;
import commands.operations.special.SabotageOperation;
import core.client.game.operations.Operation;
import core.player.PlayerComplete;
import core.player.PlayerSimple;

public class Sabotage extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2917645089871877494L;

	public Sabotage(int num, Suit suit, int id) 
	{
		super(num, suit, id);
	}

	@Override
	public String getName() 
	{
		return "Sabotage";
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		for(PlayerSimple other : player.getOtherPlayersUI())
			if(other.getHandCount() != 0 || other.isEquipped())//target must have something
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		return new SabotageOperation(player.getPlayerInfo(),this,next);
	}
}
