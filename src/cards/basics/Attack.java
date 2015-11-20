package cards.basics;

import commands.Damage.Element;
import core.client.game.operations.InitiateAttackOperation;
import core.client.game.operations.Operation;
import player.PlayerComplete;

public class Attack extends Basic {

	private static final long serialVersionUID = 4346640648436436523L;
	
	private Element element;// Normal, Fire, Thunder

	public static final String ATTACK = "Attack";
	public static final String FIRE_ATTACK = "Attack(Fire)";
	public static final String THUNDER_ATTACK = "Attack(Thunder)";

	public Attack(Element e, int num, Suit suit) {
		super(num, suit);
		element = e;
	}

	public Element getElement() {
		return element;
	}

	@Override
	public String getName() {
		switch (element) {
			case FIRE:
				return FIRE_ATTACK;
			case NORMAL:
				return ATTACK;
			case THUNDER:
				return THUNDER_ATTACK;
			default:
				return null;
		}
	}

	@Override
	public boolean isActivatableBy(PlayerComplete player) {
		return player.getAttackUsed() < player.getAttackLimit();
	}

	@Override
	public Operation generateOperation() {
		return new InitiateAttackOperation();
	}

}
