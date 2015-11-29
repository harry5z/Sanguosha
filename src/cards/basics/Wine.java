package cards.basics;

import core.client.game.operations.Operation;
import core.client.game.operations.WineOperation;
import player.PlayerComplete;

public class Wine extends Basic {

	private static final long serialVersionUID = 8731935586533627689L;

	public static final String WINE = "Wine";

	public Wine(int num, Suit suit) {
		super(num, suit);
	}

	@Override
	public String getName() {
		return WINE;
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return player.getWineUsed() < player.getWineLimit() && !player.isWineUsed();
	}

	@Override
	public Operation generateOperation() {
		return new WineOperation();
	}
}
