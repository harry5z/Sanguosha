package cards.specials.instant;

import cards.specials.Special;
import core.client.ClientGameInfo;

public abstract class Instant extends Special {
	
	private static final long serialVersionUID = -9048643013272059145L;

	public Instant(int num, Suit suit) {
		super(num, suit, true);
	}

	@Override
	public boolean isActivatable(ClientGameInfo game) {
		return true;
	}
}
