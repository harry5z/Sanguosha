package cards.specials.instant;

import player.PlayerComplete;

import commands.Command;
import commands.operations.Operation;
import commands.operations.special.DuelOperation;



public class Duel extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -301334501587512526L;
	public static final String Duel = "Duel";
	public Duel(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return Duel;
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		player.setAllTargetsSelectableExcludingSelf(true);
		return new DuelOperation(player.getPlayerInfo(),player.getCurrentStage().getSource(),this,next);
	}
}
