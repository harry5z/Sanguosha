package cards.specials.instant;

import cards.specials.Special;




public abstract class Instant extends Special
{
	public Instant(int num, int suit)
	{
		super(num, suit, true);
	}
}
