package commands.game.server.ingame;

import cards.Card;
import core.server.game.Game;
import core.server.game.controllers.PeachUsableGameController;

public class UsePeachInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9088626402130251064L;
	
	private final Card card;

	public UsePeachInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	public void execute(Game game) {
		game.<PeachUsableGameController>getGameController().onPeachUsed(card);
	}

}
