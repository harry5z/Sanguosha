package cards.specials.instant;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;
import update.operations.special_operations.BrotherhoodOperation;

public class Brotherhood extends Instant
{

	public Brotherhood(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Brotherhood";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next) 
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return new BrotherhoodOperation(player,this,next);
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player)
	{
		return true;
	}
	
}
