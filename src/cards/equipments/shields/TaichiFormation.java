package cards.equipments.shields;

import cards.Card;
import core.server.game.Damage;

public class TaichiFormation extends Shield {

	private static final long serialVersionUID = -6729114905115919334L;

	public TaichiFormation(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Taichi Formation";
	}

	@Override
	public boolean mustReactTo(Card card) {
		return true;
	}

	@Override
	public void modifyDamage(Damage damage) {

	}

}
