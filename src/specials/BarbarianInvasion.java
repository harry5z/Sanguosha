package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;


public class BarbarianInvasion extends Instant
{

	public BarbarianInvasion(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Barbarian Invasion";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return ;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return true;
	}

}
