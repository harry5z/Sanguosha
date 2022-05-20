package commands.game.server.ingame;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;

public class DiscardInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = -2110615050430784494L;

	private List<Card> cards;
	
	public DiscardInGameServerCommand(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					// TODO convert to discard controller
					game.getCurrentPlayer().discardCards(cards);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
					return;
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (cards == null || cards.isEmpty()) {
			throw new IllegalPlayerActionException("Discard: cards cannot be null or empty");
		}
		if (source.getHandCount() - cards.size() < source.getCardOnHandLimit()) {
			throw new IllegalPlayerActionException("Discard: may not discard more than needed");
		}
		
		List<Card> replacement = new ArrayList<>();
		for (Card card : cards) {
			try {
				card = game.getDeck().getValidatedCard(card);
				replacement.add(card);
			} catch (InvalidCardException e) {
				throw new IllegalPlayerActionException("Discard: Card is invalid. " + e.getMessage());
			}
			if (!source.getCardsOnHand().contains(card)) {
				throw new IllegalPlayerActionException("Discard: Player does not own the card used");
			}
		}
		cards = replacement;
	}

}
