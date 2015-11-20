package cards.basics;

import core.client.game.operations.Operation;
import player.PlayerComplete;

public class Dodge extends Basic {

	private static final long serialVersionUID = -7923623178052220181L;

	public static final String DODGE = "Dodge";

	public Dodge(int num, Suit suit) {
		super(num, suit);
	}

	@Override
	public String getName() {
		return DODGE;
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return false;
	}

	@Override
	public Operation generateOperation() {
		throw new RuntimeException("Dodge should not be activated");
	}

}
