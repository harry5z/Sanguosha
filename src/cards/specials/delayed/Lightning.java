package cards.specials.delayed;

import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.delayed.LightningOperation;
import utils.DelayedType;

public class Lightning extends Delayed {

	private static final long serialVersionUID = -8502796453884676779L;

	public Lightning(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Lightning";
	}

	@Override
	public boolean isActivatable(GameState game) {
		return !game.getSelf().hasDelayedType(DelayedType.LIGHTNING);
	}

	@Override
	public Operation generateOperation() {
		return new LightningOperation();
	}

}
