package cards.specials.delayed;

import core.GameState;
import core.client.game.operations.Operation;

public class Relaxation extends Delayed {

	private static final long serialVersionUID = -2295098976558504164L;

	public Relaxation(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Relaxation";
	}

	@Override
	public boolean isActivatable(GameState game) {
		return true;
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
