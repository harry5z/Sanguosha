package commands.game.server.ingame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.heroes.skills.SunQuanReconsiderationHeroSkill;
import core.player.PlayerCardZone;
import core.player.PlayerState;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;
import exceptions.server.game.InvalidPlayerCommandException;

public class ReconsiderationSkillInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private Map<Card, PlayerCardZone> cards;
	
	public ReconsiderationSkillInGameServerCommand(Map<Card, PlayerCardZone> cards) {
		this.cards = cards;
	}
	
	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				try {
					Set<Card> cardsOnHand = new HashSet<>();
					source.updatePlayerState(PlayerState.SUN_QUAN_RECONSIDERATION_COUNTER, 1);
					game.pushGameController(new ReceiveCardsGameController(source, game.getDeck().drawMany(cards.size()), false));
					for (Entry<Card, PlayerCardZone> entry : cards.entrySet()) {
						switch(entry.getValue()) {
							case HAND:
								cardsOnHand.add(entry.getKey());
								break;
							case EQUIPMENT:
								Equipment equipment = (Equipment) entry.getKey();
								game.pushGameController(new RecycleCardsGameController(source, Set.of(equipment)));
								game.pushGameController(new UnequipGameController(source, equipment.getEquipmentType()));
								break;
							default:
								break;
						}
					}
					// TODO: convert to discard controller
					source.discardCards(cardsOnHand);
					game.log(BattleLog
						.playerAUsedSkill(source, new SunQuanReconsiderationHeroSkill())
						.withCards(cards.keySet())
						.to("replace them with <b>" + cards.size() + "</b> cards from Deck")
					);
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (cards == null || cards.isEmpty()) {
			throw new IllegalPlayerActionException("Reconsideration: cards cannot be null or empty");
		}
		Map<Card, PlayerCardZone> map = new HashMap<>();
		for (Map.Entry<Card, PlayerCardZone> entry : cards.entrySet()) {
			Card card = null;
			try {
				card = game.getDeck().getValidatedCard(entry.getKey());
			} catch (InvalidCardException e) {
				throw new IllegalPlayerActionException("Reconsideration: Card is invalid. " + e.getMessage());
			}
			switch (entry.getValue()) {
				case HAND:
					if (!source.getCardsOnHand().contains(card)) {
						throw new IllegalPlayerActionException("Reconsideration: Player does not own the card used");
					}
					break;
				case EQUIPMENT:
					if (!(card instanceof Equipment)) {
						throw new IllegalPlayerActionException("Reconsideration: Card is not an Equipment");
					}
					if (!source.isEquippedWith((Equipment) card)) {
						throw new IllegalPlayerActionException("Reconsideration: Player is not equipped with " + card);
					}
					break;
				default:
					throw new IllegalPlayerActionException("Reconsideration: Invalid zone");
			}
			map.put(card, entry.getValue());
		}
		cards = map;
	}

}
