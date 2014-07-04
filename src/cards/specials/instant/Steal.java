package cards.specials.instant;

import commands.Command;
import commands.operations.Operation;
import commands.operations.special_operations.StealOperation;
import player.PlayerComplete;
import player.PlayerSimple;

public class Steal extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3401311157667464458L;

	public Steal(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Steal";
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next)
	{
		for(PlayerSimple other : player.getOtherPlayers())
		{
			if(!player.isPlayerInDistance(other, player.getNumberOfPlayersAlive())) //has to be in reach distance
				continue;
			if(other.getHandCount() != 0 || other.isEquipped())//target must have something
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		}
		return new StealOperation(player.getPlayerInfo(),this,next);
	}
}
