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
	public Operation onActivatedBy(PlayerClientComplete player, Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		for(PlayerClientSimple other : player.getOtherPlayers())
			if(other.getCardsOnHandCount() != 0 || other.isEquipped())//target must have something
				player.setTargetSelectable(other.getPlayerInfo(), true);
		return new SabotageOperation(player.getPlayerInfo(),this,next);
	}
}
