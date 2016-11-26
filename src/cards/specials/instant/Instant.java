package cards.specials.instant;

import cards.specials.Special;
import core.GameState;

public abstract class Instant extends Special {
	
	private static final long serialVersionUID = -9048643013272059145L;

	public Instant(int num, Suit suit, int id) {
		super(num, suit, true, id);
	}
	
	public Instant() {
		super(true);
	}

	@Override
	public boolean isActivatable(GameState game) {
		return true;
	}
}
