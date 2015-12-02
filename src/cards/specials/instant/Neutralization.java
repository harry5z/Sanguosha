package cards.specials.instant;

import core.client.ClientGameInfo;
import core.client.game.operations.Operation;

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
	public boolean isActivatable(ClientGameInfo game) {
		return false;
	}

	@Override
	public Operation generateOperation() {
		return null;
	}

}
