package cards.basics;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.UsePeachInGameServerCommand;
import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.basics.PeachOperation;
import ui.game.interfaces.Activatable;

public class Peach extends Basic {

	private static final long serialVersionUID = -302256299684511401L;
	
	public static final String PEACH = "Peach";

	public Peach(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return PEACH;
	}

	@Override
	public boolean isActivatable(GameState game) {
		return game.getSelf().getHealthCurrent() < game.getSelf().getHealthLimit();
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new PeachOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return UsePeachInGameServerCommand.class;
	}
}
