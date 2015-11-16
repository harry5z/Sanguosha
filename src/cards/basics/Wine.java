package cards.basics;

import player.PlayerComplete;

import commands.Command;
import commands.operations.Operation;
import commands.operations.WineOperation;


public class Wine extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5279754525954626716L;
	public static final String WINE = "Wine";
	public Wine(int num, Suit suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return WINE;
	}
	@Override
	public Operation onActivatedBy(PlayerComplete player, Command next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new WineOperation(player.getPlayerInfo(),this,next);
	}
	@Override
	public boolean isActivatableBy(PlayerComplete player) 
	{
		return player.getWineUsed() < player.getWineLimit() && !player.isWineUsed();
	}
}
