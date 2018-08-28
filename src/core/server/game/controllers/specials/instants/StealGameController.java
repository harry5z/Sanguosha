package core.server.game.controllers.specials.instants;

import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.event.game.instants.PlayerCardSelectionEvent;
import core.player.PlayerCardZone;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.ReceiveCardsGameController;
import core.server.game.controllers.UnequipGameController;
import core.server.game.controllers.interfaces.CardSelectableGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class StealGameController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {

	public StealGameController(PlayerInfo source, PlayerInfo target, Game game) {
		super(source, target, game);
	}

	@Override
	protected void takeEffect() {
		if (this.target.getHandCount() == 0 && !this.target.isEquipped() && this.target.getDelayedQueue().isEmpty()) {
			// if no card left on target, Sabotage is ineffective
			this.stage = this.stage.nextStage();
			this.proceed();
			return;
		}
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
	public void onCardSelected(Card card, PlayerCardZone zone) {
		this.stage = this.stage.nextStage();
		switch(zone) {
			case HAND:
				try {
					// TODO: convert to discard controller
					List<Card> cards = this.target.getCardsOnHand();
					// By default steal a random card from hand
					Card stolenCard = cards.get(new Random().nextInt(cards.size()));
					this.target.removeCardFromHand(stolenCard);
					this.game.pushGameController(new ReceiveCardsGameController(this.game, this.source, Set.of(stolenCard)));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case EQUIPMENT:
				try {
					Equipment equipment = (Equipment) card;
					this.game.pushGameController(
						new UnequipGameController(this.game, this.target, equipment.getEquipmentType())
							.setNextController(new ReceiveCardsGameController(this.game, this.source, Set.of(equipment)))
					);
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				break;
			case DELAYED:
				this.target.removeDelayed(card);
				System.out.println(card.getName());
				this.game.pushGameController(new ReceiveCardsGameController(this.game, this.source, Set.of(card)));
				break;
		}
	}

}
