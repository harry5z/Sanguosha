package commands.game.server.ingame;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.player.PlayerState;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class ReconsiderationSkillInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, PlayerCardZone> cards;
	
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
					PlayerCompleteServer source = game.getCurrentPlayer();
					source.updatePlayerState(PlayerState.SUN_QUAN_RECONSIDERATION_COUNTER, 1);
					game.pushGameController(new ReceiveCardsGameController(source, game.getDeck().drawMany(cards.size())));
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
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
			}
		};
	}

}
