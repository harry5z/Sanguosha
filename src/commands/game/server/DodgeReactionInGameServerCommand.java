package commands.game.server;

import cards.Card;
import core.server.Game;
import core.server.game.controllers.AttackGameController;

public class DodgeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 8547546299242633692L;

	private final Card dodge;
	
	public DodgeReactionInGameServerCommand(Card card) {
		this.dodge = card;
	}
	@Override
	public void execute(Game game) {
		game.<AttackGameController>getGameController().targetReacted(dodge);
	}

}
