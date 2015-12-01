package cards.specials.instant;

import core.client.game.operations.Operation;

public class BarbarianInvasion extends Instant {

	private static final long serialVersionUID = 8054906715946205031L;

	public BarbarianInvasion(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Barbarian Invasion";
	}

	@Override
	public Operation generateOperation() {
		// TODO Auto-generated method stub
		return null;
	}

}
