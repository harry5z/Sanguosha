package core.server.game.controllers.mechanics;

import commands.game.client.DiscardGameUIClientCommand;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DiscardPhaseGameController extends AbstractSingleStageGameController {

	@Override
	protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
		int amount = game.getCurrentPlayer().getHandCount() - game.getCurrentPlayer().getCardOnHandLimit();
		if (amount > 0) {
			throw new GameFlowInterruptedException(
				new DiscardGameUIClientCommand(game.getCurrentPlayer().getPlayerInfo(), amount)
			);
		}
	}

}
