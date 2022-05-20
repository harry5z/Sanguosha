package commands.game.server.ingame;

import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;

public class EndStageInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9055072795730543708L;

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.getTurnController().nextStage();
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		// nothing to validate
	}
}
