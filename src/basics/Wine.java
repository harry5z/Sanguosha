package basics;

import player.PlayerOriginalClientComplete;
import core.Basic;
import events.WineOperation;


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
	public void onActivatedBy(PlayerOriginalClientComplete player) 
	{
		player.setOperation(new WineOperation(player.getPlayerInfo(),this,player.getCurrentStage()));
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
	}
	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		if(player.getWineUsed() < player.getWineLimit() && !player.isWineUsed())
			return true;
		else
			return false;
	}
}
