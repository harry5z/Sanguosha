package cards.specials.instant;

import player.PlayerComplete;

import commands.Command;
import core.client.game.operations.Operation;


public class Neutralization extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9088689394043873593L;
	public static final String NEUTRALIZATION = "Neutralization";
	public Neutralization(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return NEUTRALIZATION;
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) 
	{
		return false;
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		return null;
	}
}
