package cards.specials.instant;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateStealInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.StealOperation;
import ui.game.interfaces.Activatable;

public class Steal extends Instant {

	private static final long serialVersionUID = -1L;

	public Steal(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Steal";
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new StealOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateStealInGameServerCommand.class;
	}
}
