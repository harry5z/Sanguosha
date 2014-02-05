package cards.specials.instant;

import player.PlayerOriginalClientComplete;
import update.Update;
import update.operations.Operation;

public class Steal extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3401311157667464458L;

	public Steal(int num, int suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return "Steal";
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
