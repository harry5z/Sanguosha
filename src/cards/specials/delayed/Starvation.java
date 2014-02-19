package cards.specials.delayed;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;

public class Starvation extends Delayed
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5348625255975178503L;

	public Starvation(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Starvation";
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player,
			Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerClientComplete player) {
		return true;
	}

}
