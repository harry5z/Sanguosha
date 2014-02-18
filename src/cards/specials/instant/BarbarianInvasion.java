package cards.specials.instant;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.BarbarianInvasionOperation;


public class BarbarianInvasion extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8054906715946205031L;

	public BarbarianInvasion(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Barbarian Invasion";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new BarbarianInvasionOperation(player,this,next);
	}
}
