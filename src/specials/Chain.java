package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;

public class Chain extends Instant
{

	public Chain(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Chain";
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
