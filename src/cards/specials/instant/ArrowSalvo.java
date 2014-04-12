package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.ArrowSalvoOperation;


public class ArrowSalvo extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1395738598490175305L;

	public ArrowSalvo(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Arrow Salvo";
	}

	@Override
	protected Operation createOperation(PlayerClientComplete player, Update next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new ArrowSalvoOperation(player,this,next);
	}
}
