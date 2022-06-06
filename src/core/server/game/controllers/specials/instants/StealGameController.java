package core.server.game.controllers.specials.instants;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.client.game.ShowCardSelectionPanelUIClientCommand;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidPlayerCommandException;
import utils.DelayedStackItem;

public class StealGameController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {

	public StealGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source, target);
	}

	@Override
	protected void takeEffect(GameInternal game) throws GameFlowInterruptedException {
		if (this.target.getHandCount() == 0 && !this.target.isEquipped() && this.target.getDelayedQueue().isEmpty()) {
			// if no card left on target, Sabotage is ineffective
			this.nextStage();
			return;
		}
		throw new GameFlowInterruptedException(
			new ShowCardSelectionPanelUIClientCommand(
				source.getPlayerInfo(),
				target,
				Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT, PlayerCardZone.DELAYED),
				Arrays.asList(EquipmentType.values())
			)
		);
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Steal on " + this.target + ", use Nullification?";
	}
	
	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
		this.nextStage();
		switch(zone) {
			case HAND:
				try {
					// TODO: convert to discard controller
					List<Card> cards = this.target.getCardsOnHand();
					// By default steal a random card from hand
					Card stolenCard = cards.get(new Random().nextInt(cards.size()));
					this.target.removeCardFromHand(stolenCard);
					game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(stolenCard), false));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case EQUIPMENT:
					Equipment equipment = (Equipment) card;
					game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(equipment), true));
					game.pushGameController(new UnequipGameController(this.target, equipment.getEquipmentType()));
				break;
			case DELAYED:
				this.target.removeDelayed(card);
				game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(card), true));
				break;
		}
	}

	@Override
	public void validateCardSelected(GameInternal game, Card card, PlayerCardZone zone) throws IllegalPlayerActionException {
		switch(zone) {
			case HAND:
				if (target.getHandCount() == 0) {
					throw new IllegalPlayerActionException("Steal: Target does not have any card on hand");
				}
				break;
			case EQUIPMENT:
				if (card == null) {
					throw new IllegalPlayerActionException("Steal: Card cannot be null");
				}
				if (!(card instanceof Equipment)) {
					throw new IllegalPlayerActionException("Steal: Card is not an Equipment");
				}
				if (!target.isEquippedWith((Equipment) card)) {
					throw new IllegalPlayerActionException("Steal: Target is not equipped with " + card);
				}
				break;
			case DELAYED:
				if (card == null) {
					throw new IllegalPlayerActionException("Steal: Card cannot be null");
				}
				boolean cardFound = false;
				for (DelayedStackItem item : target.getDelayedQueue()) {
					if (item.delayed.equals(card)) {
						cardFound = true;
					}
				}
				if (!cardFound) {
					throw new IllegalPlayerActionException("Steal: Target does not have selected Delayed card");
				}
				break;
			default:
				throw new IllegalPlayerActionException("Steal: Invalid zone");
		}		
	}

}
