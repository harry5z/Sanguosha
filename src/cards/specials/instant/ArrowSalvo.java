package cards.specials.instant;

import core.client.game.operations.Operation;

public class ArrowSalvo extends Instant {

	private static final long serialVersionUID = -1395738598490175305L;

	public ArrowSalvo(int num, Suit suit) {
		super(num, suit);
	}

	@Override
	public String getName() {
		return "Arrow Salvo";
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
