package cards.specials.instant;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateSabotageInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.SabotageOperation;
import ui.game.interfaces.Activatable;

public class Sabotage extends Instant {

	private static final long serialVersionUID = -1L;

	public Sabotage(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Sabotage";
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new SabotageOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateSabotageInGameServerCommand.class;
	}
}
