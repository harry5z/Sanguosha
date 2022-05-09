package commands.game.server.ingame;

import core.server.game.Game;
import core.server.game.controllers.mechanics.TurnGameController;

public class EndStageInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9055072795730543708L;

	@Override
	public void execute(Game game) {
		game.<TurnGameController>getGameController().nextStage();
	}

}
