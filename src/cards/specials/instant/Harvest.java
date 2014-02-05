package cards.specials.instant;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;

public class Harvest extends Instant
{

	public Harvest(int num, int suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Harvest";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,
			Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) {
		return true;
	}

}
