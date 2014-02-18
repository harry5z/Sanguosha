package cards.specials.instant;

import player.PlayerOriginalClientComplete;
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
	public Operation onActivatedBy(PlayerOriginalClientComplete player,Update next)
	{
		player.setCardOnHandSelected(this, true);
		player.setCancelEnabled(true);
		player.setConfirmEnabled(true);
		return null;
	}

	@Override
	public boolean isActivatableBy(PlayerOriginalClientComplete player) 
	{
		return false;
	}
}
