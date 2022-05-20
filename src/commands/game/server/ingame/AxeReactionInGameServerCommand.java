package commands.game.server.ingame;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cards.Card;
import cards.equipments.Equipment;
import core.player.PlayerCardZone;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.equipment.AxeGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class AxeReactionInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private Map<Card, PlayerCardZone> cards;
	
	/**
	 * 
	 * @param cards : Two cards used for Axe Reaction
	 */
	public AxeReactionInGameServerCommand(Map<Card, PlayerCardZone> cards) {
		this.cards = cards;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				AxeGameController controller = game.<AxeGameController>getNextGameController();
				for (Entry<Card, PlayerCardZone> entry : cards.entrySet()) {
					controller.onCardSelected(game, entry.getKey(), entry.getValue());
				}
				controller.onDecisionMade(cards.size() == 2);				
			}
		};

	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (cards == null) {
			throw new IllegalPlayerActionException("Axe Reaction: cards cannot be null");
		}
		if (cards.size() != 0 || cards.size() != 2) {
			throw new IllegalPlayerActionException("Axe Reaction: cards size must be either 0 or 2");
		}
		Map<Card, PlayerCardZone> map = new HashMap<>();
		if (cards.size() == 2) {
			for (Map.Entry<Card, PlayerCardZone> entry : cards.entrySet()) {
				Card card = null;
				try {
					card = game.getDeck().getValidatedCard(entry.getKey());
				} catch (InvalidCardException e) {
					throw new IllegalPlayerActionException("Axe Reaction: Card is invalid. " + e.getMessage());
				}
				switch (entry.getValue()) {
					case HAND:
						if (!source.getCardsOnHand().contains(card)) {
							throw new IllegalPlayerActionException("Axe Reaction: Player does not own the card used");
						}
						break;
					case EQUIPMENT:
						if (!(card instanceof Equipment)) {
							throw new IllegalPlayerActionException("Axe Reaction: Card is not an Equipment");
						}
						if (!source.isEquippedWith((Equipment) card)) {
							throw new IllegalPlayerActionException("Axe Reaction: Player is not equipped with " + card);
						}
						break;
					default:
						throw new IllegalPlayerActionException("Axe Reaction: Invalid zone");
				}
				map.put(card, entry.getValue());
			}
		}
		cards = map;
	}

}
