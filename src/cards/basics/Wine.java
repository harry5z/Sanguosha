package cards.basics;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.WineOperation;


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
	public Operation onActivatedBy(PlayerClientComplete player, Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new WineOperation(player.getPlayerInfo(),this,next);
	}
	@Override
	public boolean isActivatableBy(PlayerClientComplete player) 
	{
		return player.getWineUsed() < player.getWineLimit() && !player.isWineUsed();
	}
}
