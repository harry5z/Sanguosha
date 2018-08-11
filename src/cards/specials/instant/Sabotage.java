package cards.specials.instant;

import core.client.game.operations.Operation;

public class Sabotage extends Instant {

	private static final long serialVersionUID = -1L;

	public Sabotage(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Sabotage";
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}
}
