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
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class SabotageGameController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {

	public SabotageGameController(PlayerInfo source, PlayerInfo target, Game game) {
		super(source, target, game);
	}
	
	@Override
	protected void takeEffect() throws GameFlowInterruptedException {
		if (this.target.getHandCount() == 0 && !this.target.isEquipped() && this.target.getDelayedQueue().isEmpty()) {
			// if no card left on target, Sabotage is ineffective
			this.nextStage();
			return;
		}
		this.game.emit(new PlayerCardSelectionEvent(
			this.source.getPlayerInfo(),
			this.target.getPlayerInfo(),
			Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT, PlayerCardZone.DELAYED)
		));
		throw new GameFlowInterruptedException();
	}
	
	@Override
	protected String getNeutralizationMessage() {
		return this.source + " used Sabotage on " + this.target + ", use Neutralization?";
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone zone) {
		switch(zone) {
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
					this.game.pushGameController(new RecycleCardsGameController(this.game, this.target, Set.of(equipment)));
					this.game.pushGameController(new UnequipGameController(this.game, this.target, equipment.getEquipmentType()));
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				break;
			case DELAYED:
				this.target.removeDelayed(card);
				this.game.pushGameController(new RecycleCardsGameController(this.game, this.target, Set.of(card)));
				break;
		}
		this.nextStage();
	}
}
