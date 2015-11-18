package cards.specials.instant;

import player.PlayerComplete;

import commands.Command;
import commands.operations.special.CreationOperation;
import core.client.game.operations.Operation;

public class Creation extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5695736855862242617L;
	public static final String CREATION = "Creation";
	public Creation(int num, Suit suit)
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return CREATION;
	}

	@Override
	protected Operation createOperation(PlayerComplete player, Command next) 
	{
		player.getGameListener().setConfirmEnabled(true);
		return new CreationOperation(player.getPlayerInfo(),player.getCurrentStage().getSource(),this,next);
	}
}
