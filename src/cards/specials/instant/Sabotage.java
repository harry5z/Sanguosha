package cards.specials.instant;

import player.PlayerClientComplete;
import player.PlayerClientSimple;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.SabotageOperation;

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
	protected Operation createOperation(PlayerClientComplete player, Update next) 
	{
		for(PlayerClientSimple other : player.getOtherPlayers())
			if(other.getCardsOnHandCount() != 0 || other.isEquipped())//target must have something
				player.getGameListener().setTargetSelectable(other.getPlayerInfo(), true);
		return new SabotageOperation(player.getPlayerInfo(),this,next);
	}
}
