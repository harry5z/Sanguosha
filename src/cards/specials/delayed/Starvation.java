package cards.specials.delayed;

import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.delayed.StarvationOperation;

public class Starvation extends Delayed {

	private static final long serialVersionUID = -5348625255975178503L;

	public Starvation(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Starvation";
	}

	@Override
	public boolean isActivatable(GameState game) {
		return true;
	}

	@Override
	public Operation generateOperation() {
		return new StarvationOperation();
	}

}
