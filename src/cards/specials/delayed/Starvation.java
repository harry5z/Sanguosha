package cards.specials.delayed;

import commands.Command;
import core.client.game.operations.Operation;
import player.PlayerComplete;

public class Starvation extends Delayed
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5348625255975178503L;

	public Starvation(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Starvation";
	}

	@Override
	public Operation onActivatedBy(PlayerComplete player,
			Command next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return true;
	}

}
