package cards.specials.delayed;

import cards.specials.Special;

public abstract class Delayed extends Special {

	private static final long serialVersionUID = -8220927012748306449L;

	public Delayed(int num, Suit suit) {
		super(num, suit, false);
	}
}
