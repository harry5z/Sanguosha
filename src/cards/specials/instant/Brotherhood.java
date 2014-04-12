package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.BrotherhoodOperation;

public class Brotherhood extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8104953282875316885L;

	public Brotherhood(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Brotherhood";
	}

	@Override
	public Operation createOperation(PlayerClientComplete player, Update next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new BrotherhoodOperation(player,this,next);
	}
}
