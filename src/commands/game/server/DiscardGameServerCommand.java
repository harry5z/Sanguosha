package commands.game.server;

import java.util.List;

import cards.Card;
import core.server.Game;
import core.server.game.controllers.TurnGameController;

public class DiscardGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = -2110615050430784494L;

	private final List<Card> cards;
	
	public DiscardGameServerCommand(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public void execute(Game game) {
		game.<TurnGameController>getGameController().getCurrentPlayer().discardCards(cards);
		game.<TurnGameController>getGameController().proceed();
	}

}
