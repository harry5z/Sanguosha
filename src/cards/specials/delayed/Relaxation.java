package cards.specials.delayed;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;

public class Relaxation extends Delayed
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2295098976558504164L;

	public Relaxation(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Relaxation";
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player,
			Update next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerClientComplete player) {
		// TODO Auto-generated method stub
		return true;
	}

}
