package cards.basics;

import core.client.game.operations.Operation;
import core.client.game.operations.PeachOperation;
import player.PlayerComplete;

public class Peach extends Basic {

	private static final long serialVersionUID = -302256299684511401L;
	
	public static final String PEACH = "Peach";

	public Peach(int num, Suit suit) {
		super(num, suit);
	}

	@Override
	public String getName() {
		return PEACH;
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return player.getHealthCurrent() < player.getHealthLimit();
	}

	@Override
	public Operation generateOperation() {
		return new PeachOperation();
	}
}
