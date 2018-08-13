package core.server.game.controllers.specials.instants;

import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.event.game.instants.PlayerCardSelectionEvent;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.controllers.UnequipGameController;
import core.server.game.controllers.interfaces.CardSelectableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class SabotageGameController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {

	public SabotageGameController(PlayerInfo source, PlayerInfo target, GameRoom room) {
		super(source, target, room);
	}
	
	@Override
	protected void takeEffect() {
		try {
			this.game.emit(new PlayerCardSelectionEvent(
				this.source.getPlayerInfo(),
				this.target.getPlayerInfo(),
				Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT, PlayerCardZone.DELAYED)
			));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone area) {
		this.stage = this.stage.nextStage();
		switch(area) {
			case HAND:
				try {
					// TODO: convert to discard controller
					List<Card> cards = this.target.getCardsOnHand();
					// By default discard a random card from hand
					this.target.discardCard(cards.get(new Random().nextInt(cards.size())));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case EQUIPMENT:
				try {
					Equipment equipment = (Equipment) card;
					this.game.pushGameController(new UnequipGameController(this.game, this.target, equipment.getEquipmentType()));
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				break;
			case DELAYED:
				// TODO: implement
				break;
		}
		this.game.getGameController().proceed();
	}
}
