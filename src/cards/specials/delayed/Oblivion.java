package cards.specials.delayed;

import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.delayed.OblivionOperation;
import ui.game.interfaces.Activatable;

public class Oblivion extends Delayed {

	private static final long serialVersionUID = -2295098976558504164L;

	public Oblivion(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Oblivion";
	}

	@Override
	public boolean isActivatable(GameState game) {
		return true;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new OblivionOperation(source);
	}

}
