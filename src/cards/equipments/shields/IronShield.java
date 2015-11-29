package cards.equipments.shields;

import cards.Card;
import cards.basics.Attack;
import core.server.game.Damage;

public class IronShield extends Shield {

	private static final long serialVersionUID = 4370802087723597065L;

	public IronShield(int num, Suit suit) {
		super(num, suit);
	}

	@Override
	public String getName() {
		return "Iron Shield";
	}

	@Override
	public boolean mustReactTo(Card card) {
		return !(card instanceof Attack && card.getColor() == Color.BLACK);
	}

	@Override
	public void modifyDamage(Damage damage) {

	}

}
