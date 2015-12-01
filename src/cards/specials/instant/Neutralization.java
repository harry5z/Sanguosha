package cards.specials.instant;

import core.client.game.operations.Operation;
import core.player.PlayerComplete;

public class Neutralization extends Instant {

	private static final long serialVersionUID = -9088689394043873593L;

	public static final String NEUTRALIZATION = "Neutralization";

	public Neutralization(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return NEUTRALIZATION;
	}

	@Override
	public boolean isActivatable(PlayerComplete player) {
		return false;
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
