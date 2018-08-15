package cards.specials.instant;

import core.client.game.operations.Operation;
import core.client.game.operations.instants.FireAttackOperation;

public class FireAttack extends Instant {
	private static final long serialVersionUID = -1L;
	public static final String FIRE_ATTACK = "Fire Attack";

	public FireAttack(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return FIRE_ATTACK;
	}

	@Override
	public Operation generateOperation() {
		return new FireAttackOperation();
	}
}
