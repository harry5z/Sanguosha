package cards.specials.instant;

import core.client.game.operations.Operation;
import core.client.game.operations.instants.ArrowSalvoOperation;

public class ArrowSalvo extends Instant {

	private static final long serialVersionUID = -1395738598490175305L;

	public static final String ARROW_SALVO = "Arrow Salvo";

	public ArrowSalvo(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return ARROW_SALVO;
	}

	@Override
	public Operation generateOperation() {
		return new ArrowSalvoOperation();
	}

}
