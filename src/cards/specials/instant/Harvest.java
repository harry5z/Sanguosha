package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;

public class Harvest extends Instant
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3333097112836442800L;

	public Harvest(int num, Suit suit)
	{
		super(num, suit);
	}

	@Override
	public String getName()
	{
		return "Harvest";
	}

	@Override
	public Operation onActivatedBy(PlayerClientComplete player, Update next) 
	{
		return null;
	}
}
