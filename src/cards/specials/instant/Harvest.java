package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.HarvestOperation;

public class Harvest extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3333097112836442800L;

	public Harvest(int num, Suit suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Harvest";
	}

	@Override
	protected Operation createOperation(PlayerClientComplete player, Update next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new HarvestOperation(player,this,next);
	}
}
