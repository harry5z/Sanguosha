package specials;

import player.PlayerOriginalClientComplete;
import core.Operation;
import core.Update;


public class Neutralization extends Instant
{

	public Neutralization(int num, int suit) 
	{
		super(num, suit);
		
	}

	@Override
	public String getName() 
	{
		return "Neutralization";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return false;
	}

}
