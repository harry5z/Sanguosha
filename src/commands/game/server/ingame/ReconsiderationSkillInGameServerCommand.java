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
import core.server.game.Game;
import core.server.game.controllers.ReceiveCardsGameController;
import core.server.game.controllers.RecycleCardsGameController;
import core.server.game.controllers.UnequipGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class ReconsiderationSkillInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Map<Card, PlayerCardZone> cards;
	
	public ReconsiderationSkillInGameServerCommand(Map<Card, PlayerCardZone> cards) {
		this.cards = cards;
	}
	
	@Override
	public void execute(Game game) {
		try {
			Set<Card> cardsOnHand = new HashSet<>();
			PlayerCompleteServer source = game.getCurrentPlayer();
			source.updatePlayerState(PlayerState.SUN_QUAN_RECONSIDERATION_COUNTER, 1);
			game.pushGameController(new ReceiveCardsGameController(game, source, game.getDeck().drawMany(this.cards.size())));
			for (Entry<Card, PlayerCardZone> entry : this.cards.entrySet()) {
				switch(entry.getValue()) {
					case HAND:
						cardsOnHand.add(entry.getKey());
						break;
					case EQUIPMENT:
						Equipment equipment = (Equipment) entry.getKey();
						game.pushGameController(
							new UnequipGameController(game, source, equipment.getEquipmentType())
								.setNextController(new RecycleCardsGameController(game, source, Set.of(equipment))
							)
						);
						break;
					default:
						break;
				}
			}
			// TODO: convert to discard controller
			source.discardCards(cardsOnHand);
			game.getGameController().proceed();
		} catch (InvalidPlayerCommandException e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

}
