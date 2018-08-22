package commands.game.server.ingame;

import core.server.game.Game;
import core.server.game.controllers.interfaces.DecisionRequiredGameController;

public class DecisionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final boolean confirmed;
	
	public DecisionInGameServerCommand(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	@Override
	public void execute(Game game) {
		game.<DecisionRequiredGameController>getGameController().onDecisionMade(this.confirmed);
		game.getGameController().proceed();
	}

}
