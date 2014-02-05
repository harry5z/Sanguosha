package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;

public class Relaxation extends Delayed
{

	public Relaxation(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Relaxation";
	}

	@Override
	public Operation onActivatedBy(PlayerOriginalClientComplete player,
			Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) {
		// TODO Auto-generated method stub
		return true;
	}

}
