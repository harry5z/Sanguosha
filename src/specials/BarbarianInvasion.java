package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;
import events.special_events.BarbarianInvasionOperation;


public class BarbarianInvasion extends Instant
{

	public BarbarianInvasion(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Barbarian_Invasion";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player, Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new BarbarianInvasionOperation(player,this,next);
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return true;
	}

}
