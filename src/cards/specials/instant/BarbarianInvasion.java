package cards.specials.instant;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateBarbarianInvasionInGameServerCommand;
import core.client.game.operations.Operation;
import core.client.game.operations.instants.BarbarianInvasionOperation;
import ui.game.interfaces.Activatable;

public class BarbarianInvasion extends Instant {

	private static final long serialVersionUID = 8054906715946205031L;
	
	public static final String BARBARIAN_INVASION = "Barbarian Invasion";

	public BarbarianInvasion(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return BARBARIAN_INVASION;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new BarbarianInvasionOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateBarbarianInvasionInGameServerCommand.class;
	}

}
