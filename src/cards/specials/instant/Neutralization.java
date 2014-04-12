package cards.specials.instant;

import player.PlayerClientComplete;
import update.Update;
import update.operations.Operation;


public class Neutralization extends Instant
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9088689394043873593L;
	public static final String NEUTRALIZATION = "Neutralization";
	public Neutralization(int num, Suit suit) 
	{
		super(num, suit);
	}

	@Override
	public String getName() 
	{
		return NEUTRALIZATION;
	}

	@Override
	public boolean isActivatableBy(PlayerClientComplete player) 
	{
		return false;
	}

	@Override
	protected Operation createOperation(PlayerClientComplete player, Update next) 
	{
		return null;
	}
}
