package basics;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.WineOperation;
import core.Basic;
import core.Operation;


public class Wine extends Basic
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5279754525954626716L;
	public static final String WINE = "Wine";
	public Wine(int num, int suit)
	{
		super(num, suit);
	}
	@Override
	public String getName()
	{
		return WINE;
	}
	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new WineOperation(player.getPlayerInfo(),this,next);
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return player.getWineUsed() < player.getWineLimit() && !player.isWineUsed();
	}
}
