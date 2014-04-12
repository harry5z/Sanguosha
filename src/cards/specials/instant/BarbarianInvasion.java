package cards.specials.instant;

import player.PlayerClientComplete;
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
	protected Operation createOperation(PlayerClientComplete player, Update next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new BarbarianInvasionOperation(player,this,next);
	}
}
