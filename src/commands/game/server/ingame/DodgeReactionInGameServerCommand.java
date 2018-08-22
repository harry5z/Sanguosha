package commands.game.server.ingame;

import cards.Card;
import core.server.game.Game;
import core.server.game.controllers.DodgeGameController;

public class DodgeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8547546299242633692L;

	private final Card dodge;
	
	public DodgeReactionInGameServerCommand(Card card) {
		this.dodge = card;
	}
	
	@Override
	public void execute(Game game) {
		if (dodge != null) {
			game.<DodgeGameController>getGameController().onDodgeUsed(dodge);
		} else {
			game.<DodgeGameController>getGameController().onDodgeNotUsed();
		}
		game.getGameController().proceed();
	}

}
