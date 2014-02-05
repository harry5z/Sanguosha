package cards.basics;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.PeachOperation;


public class Peach extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4464520689683075435L;
	public static final String PEACH = "Peach";
	public Peach(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return PEACH;
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return player.getHealthCurrent() < player.getHealthLimit();
	}
	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new PeachOperation(player.getPlayerInfo(),this,next);
	}
}
