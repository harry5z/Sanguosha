package cards.specials.instant;

import core.client.game.operations.Operation;
import core.client.game.operations.instants.ChainOperation;

public class Chain extends Instant {

	private static final long serialVersionUID = -1L;

	public Chain(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Chain";
	}

	@Override
	public Operation generateOperation() {
		return new ChainOperation();
	}
}
