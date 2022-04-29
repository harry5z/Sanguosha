package cards.specials.instant;

import core.client.game.operations.Operation;
import core.client.game.operations.instants.CreationOperation;
import ui.game.interfaces.Activatable;

public class Creation extends Instant {
	
	private static final long serialVersionUID = -5695736855862242617L;

	public static final String CREATION = "Creation";

	public Creation(int num, Suit suit, int id) {
		super(num, suit, id);
	}
	
	public Creation() {}

	@Override
	public String getName() {
		return CREATION;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new CreationOperation(source);
	}
}
