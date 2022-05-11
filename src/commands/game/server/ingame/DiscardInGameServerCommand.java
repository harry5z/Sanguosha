package commands.game.server.ingame;

import java.util.List;

import cards.Card;
import core.server.game.Game;
import exceptions.server.game.InvalidPlayerCommandException;

public class DiscardInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = -2110615050430784494L;

	private final List<Card> cards;
	
	public DiscardInGameServerCommand(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public void execute(Game game) {
		try {
			// TODO convert to discard controller
			game.getCurrentPlayer().discardCards(cards);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
			return;
		}
	}

}
