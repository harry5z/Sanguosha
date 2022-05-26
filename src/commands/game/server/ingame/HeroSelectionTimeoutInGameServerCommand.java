package commands.game.server.ingame;

import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.GameStartGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;

public class HeroSelectionTimeoutInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.<GameStartGameController>getNextGameController().setDefaultHeroSelection();
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		// nothing to validate as it is a server side command
	}

}
