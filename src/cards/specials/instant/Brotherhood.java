package cards.specials.instant;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateBrotherhoodInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.BrotherhoodOperation;
import ui.game.interfaces.Activatable;

public class Brotherhood extends Instant {

	private static final long serialVersionUID = 1L;

	public static final String BROTHERHOOD = "Brotherhood";

	public Brotherhood(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return BROTHERHOOD;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new BrotherhoodOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateBrotherhoodInGameServerCommand.class;
	}
}
