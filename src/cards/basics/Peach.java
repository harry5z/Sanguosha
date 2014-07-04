package cards.basics;

import commands.Command;
import commands.operations.Operation;
import commands.operations.PeachOperation;
import player.PlayerComplete;


public class Peach extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4464520689683075435L;
	public static final String PEACH = "Peach";
	public Peach(int num, Suit suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return PEACH;
	}
	@Override
	public boolean isActivatableBy(PlayerComplete player) 
	{
		return player.getHealthCurrent() < player.getHealthLimit();
	}
	@Override
	public Operation onActivatedBy(PlayerComplete player, Command next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new PeachOperation(player.getPlayerInfo(),this,next);
	}
}
