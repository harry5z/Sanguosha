package cards.specials.delayed;

import core.client.ClientGameInfo;
import core.client.game.operations.Operation;

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
	public boolean isActivatable(ClientGameInfo game) {
		return true;
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
