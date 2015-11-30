package cards.specials;

import cards.Card;

/**
 * The "special" type of cards, including Steal, Lightning, etc.
 * 
 * @author Harry
 *
 */
public abstract class Special extends Card {

	private static final long serialVersionUID = 1458323578635106662L;

	private boolean instant;

	public Special(int num, Suit suit, boolean isInstant) {
		super(num, suit, CardType.SPECIAL);
		instant = isInstant;
	}

	public boolean isInstant() {
		return instant;
	}
}
