package cards.equipments.shields;

import cards.Card;
import core.server.game.Damage;

public class SilverLion extends Shield {

	private static final long serialVersionUID = -4821532886423359596L;

	public SilverLion(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Silver Lion";
	}

	@Override
	public boolean mustReactTo(Card card) {
		return true;
	}

	@Override
	public void modifyDamage(Damage damage) {
		if (damage.getAmount() > 1) {
			damage.setAmount(1);
		}
	}

}
