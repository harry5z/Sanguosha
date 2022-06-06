package commands.server.ingame;

import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;

public class DecisionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final boolean confirmed;
	
	public DecisionInGameServerCommand(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.<DecisionRequiredGameController>getNextGameController().onDecisionMade(confirmed);
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		// nothing to validate
	}

}
