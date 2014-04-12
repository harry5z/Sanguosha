package cards.specials.instant;

import player.PlayerClientComplete;
import player.PlayerClientSimple;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.StealOperation;

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
	protected Operation createOperation(PlayerClientComplete player, Update next)
	{
		for(PlayerClientSimple other : player.getOtherPlayers())
		{
			if(!player.isPlayerInDistance(other, player.getNumberOfPlayersAlive())) //has to be in reach distance
				continue;
			if(other.getCardsOnHandCount() != 0 || other.isEquipped())//target must have something
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		}
		return new StealOperation(player.getPlayerInfo(),this,next);
	}
}
