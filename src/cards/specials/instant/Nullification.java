package cards.specials.instant;

import core.GameState;
import core.client.game.operations.Operation;
import ui.game.interfaces.Activatable;

public class Nullification extends Instant {

	private static final long serialVersionUID = -9088689394043873593L;

	public static final String NULLIFICATION = "Nullification";

	public Nullification(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return NULLIFICATION;
	}
	
	@Override
	public boolean isActivatable(GameState game) {
		return false;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return null;
	}

}
