package commands.game.server.ingame;

import cards.Card;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.DodgeGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DodgeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8547546299242633692L;

	private final Card dodge;
	
	public DodgeReactionInGameServerCommand(Card card) {
		this.dodge = card;
	}
	
	@Override
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(Game game) throws GameFlowInterruptedException {
				if (dodge != null) {
					game.<DodgeGameController>getNextGameController().onDodgeUsed(game, dodge);
				} else {
					game.<DodgeGameController>getNextGameController().onDodgeNotUsed();
				}
			}
		};
	}
	
}
