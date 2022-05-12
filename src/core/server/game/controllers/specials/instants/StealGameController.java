package core.server.game.controllers.specials.instants;

import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.event.game.instants.PlayerCardSelectionEvent;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class StealGameController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {

	public StealGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		super(source, target);
	}

	@Override
	protected void takeEffect(Game game) throws GameFlowInterruptedException {
		if (this.target.getHandCount() == 0 && !this.target.isEquipped() && this.target.getDelayedQueue().isEmpty()) {
			// if no card left on target, Sabotage is ineffective
			this.nextStage();
			return;
		}
		game.emit(new PlayerCardSelectionEvent(
			this.source.getPlayerInfo(),
			this.target.getPlayerInfo(),
			Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT, PlayerCardZone.DELAYED)
		));
		throw new GameFlowInterruptedException();
	}
	
	@Override
	protected String getNullificationMessage() {
		return this.source + " used Steal on " + this.target + ", use Nullification?";
	}
	
	@Override
	public void onCardSelected(Game game, Card card, PlayerCardZone zone) {
		this.nextStage();
		switch(zone) {
			case HAND:
				try {
					// TODO: convert to discard controller
					List<Card> cards = this.target.getCardsOnHand();
					// By default steal a random card from hand
					Card stolenCard = cards.get(new Random().nextInt(cards.size()));
					this.target.removeCardFromHand(stolenCard);
					game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(stolenCard)));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case EQUIPMENT:
				try {
					Equipment equipment = (Equipment) card;
					game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(equipment)));
					game.pushGameController(new UnequipGameController(this.target, equipment.getEquipmentType()));
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				break;
			case DELAYED:
				this.target.removeDelayed(card);
				game.pushGameController(new ReceiveCardsGameController(this.source, Set.of(card)));
				break;
		}
	}

}
