package commands.game.server;

import java.util.List;

import cards.Card;
import core.server.Game;
import core.server.game.controllers.TurnGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class DiscardGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = -2110615050430784494L;

	private final List<Card> cards;
	
	public DiscardGameServerCommand(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public void execute(Game game) {
		try {
			game.<TurnGameController>getGameController().getCurrentPlayer().discardCards(cards);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
		game.<TurnGameController>getGameController().proceed();
	}

}
