package cards.specials.instant;

import cards.specials.Special;
import player.PlayerComplete;

public abstract class Instant extends Special {
	
	private static final long serialVersionUID = -9048643013272059145L;

	public Instant(int num, Suit suit) {
		super(num, suit, true);
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return true;
	}
}
