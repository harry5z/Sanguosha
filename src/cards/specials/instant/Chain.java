package cards.specials.instant;

import player.PlayerComplete;

import commands.Command;
import core.client.game.operations.Operation;

public class Chain extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842163599907076818L;

	public Chain(int num, Suit suit, int id) 
	{
		super(num, suit, id);
	}

	@Override
	public String getName() 
	{
		return "Chain";
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) {
		// TODO Auto-generated method stub
		return null;
	}
}
