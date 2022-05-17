package cards.specials.instant;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateDuelInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.DuelOperation;
import ui.game.interfaces.Activatable;

public class Duel extends Instant {
	private static final long serialVersionUID = -1L;
	public static final String DUEL = "Duel";

	public Duel(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return DUEL;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new DuelOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateDuelInGameServerCommand.class;
	}
}
