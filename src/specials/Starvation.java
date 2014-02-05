package specials;

import player.PlayerOriginalClientComplete;
import update.Update;
import core.Operation;

public class Starvation extends Delayed
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5348625255975178503L;

	public Starvation(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Starvation";
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
