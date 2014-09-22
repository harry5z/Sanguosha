package cards.specials.instant;

import commands.Command;
import commands.operations.Operation;
import commands.operations.special.HarvestOperation;
import player.PlayerComplete;

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
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new HarvestOperation(player,this,next);
	}
}
