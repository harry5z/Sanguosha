package cards.specials.instant;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateHarvestInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.HarvestOperation;
import ui.game.interfaces.Activatable;

public class Harvest extends Instant {

	private static final long serialVersionUID = -1L;

	public Harvest(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Harvest";
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new HarvestOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateHarvestInGameServerCommand.class;
	}
}
