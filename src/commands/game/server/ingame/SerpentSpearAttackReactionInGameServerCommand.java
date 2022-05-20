package commands.game.server.ingame;

import java.util.HashSet;
import java.util.Set;

import cards.Card;
import cards.Card.Color;
import cards.basics.Attack;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.AttackUsableGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class SerpentSpearAttackReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;

	private Set<Card> cards;
	
	public SerpentSpearAttackReactionInGameServerCommand(Set<Card> cards) {
		this.cards = cards;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				Color color = cards.stream().map(card -> card.getColor()).reduce(
					cards.iterator().next().getColor(),
					(c1, c2) -> c1 == c2 ? c1 : Color.COLORLESS
				);
				game.<AttackUsableGameController>getNextGameController().onAttackUsed(game, new Attack(color));
				game.pushGameController(new UseCardOnHandGameController(source, cards));
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (cards == null || cards.size() != 2) {
			throw new IllegalPlayerActionException("Serpent Spear: Must use 2 cards");
		}
		Set<Card> replacement = new HashSet<>();
		for (Card card : cards) {
			try {
				card = game.getDeck().getValidatedCard(card);
				replacement.add(card);
			} catch (InvalidCardException e) {
				throw new IllegalPlayerActionException("Serpent Spear: Card is invalid. " + e.getMessage());
			}
			if (!source.getCardsOnHand().contains(card)) {
				throw new IllegalPlayerActionException("Serpent Spear: Player does not own the card used");
			}
		}
		cards = replacement;
	}

}
