package core.server.game.controllers.mechanics;

import commands.game.client.DealStartGameUIClientCommmand;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DealPhaseGameController extends AbstractSingleStageGameController {

	@Override
	protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new DealStartGameUIClientCommmand(game.getCurrentPlayer().getPlayerInfo())
		);
	}

}
