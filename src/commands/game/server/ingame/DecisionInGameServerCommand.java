package commands.game.server.ingame;

import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DecisionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final boolean confirmed;
	
	public DecisionInGameServerCommand(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	@Override
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(Game game) throws GameFlowInterruptedException {
				game.<DecisionRequiredGameController>getNextGameController().onDecisionMade(confirmed);
			}
		};
	}

}
