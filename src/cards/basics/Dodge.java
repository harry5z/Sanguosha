package cards.basics;

import commands.game.server.ingame.InGameServerCommand;
import core.GameState;
import core.client.game.operations.Operation;
import ui.game.interfaces.Activatable;

public class Dodge extends Basic {

	private static final long serialVersionUID = -7923623178052220181L;

	public static final String DODGE = "Dodge";

	public Dodge(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return DODGE;
	}

	@Override
	public boolean isActivatable(GameState game) {
		return false;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		throw new RuntimeException("Dodge should not be activated");
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return null;
	}

}
