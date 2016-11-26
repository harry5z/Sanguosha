package cards.basics;

import core.GameState;
import core.client.game.operations.Operation;

public class Dodge extends Basic {

	private static final long serialVersionUID = -7923623178052220181L;

	public static final String DODGE = "Dodge";

	public Dodge(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return DODGE;
	}

	@Override
	public boolean isActivatable(GameState game) {
		return false;
	}

	@Override
	public Operation generateOperation() {
		throw new RuntimeException("Dodge should not be activated");
	}

}
