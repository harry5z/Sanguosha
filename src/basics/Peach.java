package basics;

import player.PlayerOriginalClientComplete;
import core.Basic;
import events.PeachOperation;


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
		if(player.getHealthCurrent() < player.getHealthLimit())
			return true;
		else
			return false;
	}
	@Override
	public void onActivatedBy(PlayerOriginalClientComplete player)
	{
		player.setOperation(new PeachOperation(player.getPlayerInfo(),this,player.getCurrentStage()));
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
	}
}
