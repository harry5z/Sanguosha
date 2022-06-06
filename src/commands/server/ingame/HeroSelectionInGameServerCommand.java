package commands.server.ingame;

import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.GameStartGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;

public class HeroSelectionInGameServerCommand extends InGameServerCommand {
	
	private static final long serialVersionUID = 1L;
	
	private final int index;
	
	public HeroSelectionInGameServerCommand(int index) {
		this.index = index;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.<GameStartGameController>getNextGameController().setHeroSelection(source, index);
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		game.<GameStartGameController>getCurrentGameController().validateHeroSelection(source, index);
	}

}
