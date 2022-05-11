package commands.game.server.ingame;

import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.TurnGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class EndStageInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9055072795730543708L;

	@Override
	protected GameController getGameController(Game game) {
		return new AbstractSingleStageGameController(game) {
			
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				game.<TurnGameController>getNextGameController().nextStage();
			}
		};
	}
}
