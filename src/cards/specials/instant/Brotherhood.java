package cards.specials.instant;

import commands.Command;
import commands.operations.special.BrotherhoodOperation;
import core.client.game.operations.Operation;
import player.PlayerComplete;

public class Brotherhood extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8104953282875316885L;

	public Brotherhood(int num, Suit suit, int id) 
	{
		super(num, suit, id);
	}

	@Override
	public String getName()
	{
		return "Brotherhood";
	}

	@Override
	public Operation createOperation(PlayerComplete player, Command next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new BrotherhoodOperation(player,this,next);
	}
}
