package cards.basics;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateAttackInGameServerCommand;
import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.InitiateAttackOperation;
import core.server.game.Damage.Element;
import ui.game.interfaces.Activatable;

public class Attack extends Basic {

	private static final long serialVersionUID = 4346640648436436523L;
	
	private Element element;// Normal, Fire, Thunder

	public static final String ATTACK = "Attack";
	public static final String FIRE_ATTACK = "Attack(Fire)";
	public static final String THUNDER_ATTACK = "Attack(Thunder)";
	
	/**
	 * Creates a transformed Attack card
	 */
	public Attack() {
		element = Element.NORMAL;
	}

	public Attack(Element e, int num, Suit suit, int id) {
		super(num, suit, id);
		element = e;
	}
	
	public Attack(Element e, int num, Suit suit) {
		super(num, suit);
		this.element = e;
	}
	
	public Attack(Color c) {
		super(c);
		this.element = Element.NORMAL;
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
	public boolean isActivatable(GameState game) {
		return game.getSelf().getAttackUsed() < game.getSelf().getAttackLimit();
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new InitiateAttackOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateAttackInGameServerCommand.class;
	}

}
