package cards.specials.instant;

import player.PlayerClientComplete;
import cards.specials.Special;

public abstract class Instant extends Special
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9048643013272059145L;

	public Instant(int num, Suit suit)
	{
		super(num, suit, true);
	}
	
	@Override
	public boolean isActivatableBy(PlayerClientComplete player) 
	{
		return true;
	}
}
