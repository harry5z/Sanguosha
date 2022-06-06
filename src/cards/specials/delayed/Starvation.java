package cards.specials.delayed;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateStarvationInGameServerCommand;
import core.GameState;
import core.client.game.operations.Operation;
import core.client.game.operations.delayed.StarvationOperation;
import ui.game.interfaces.Activatable;

public class Starvation extends Delayed {

	private static final long serialVersionUID = -5348625255975178503L;

	public Starvation(int num, Suit suit, int id) {
		super(num, suit, id);
	}

	@Override
	public String getName() {
		return "Starvation";
	}

	@Override
	public boolean isActivatable(GameState game) {
		return true;
	}

	@Override
	public Operation generateOperation(Activatable source) {
		return new StarvationOperation(source);
	}

	@Override
	public Class<? extends InGameServerCommand> getAllowedDealPhaseResponseType() {
		return InitiateStarvationInGameServerCommand.class;
	}

}
