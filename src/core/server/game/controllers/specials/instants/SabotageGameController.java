package core.server.game.controllers.specials.instants;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.game.client.ShowCardSelectionPanelUIClientCommand;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class SabotageGameController extends SingleTargetInstantSpecialGameController implements CardSelectableGameController {

	public SabotageGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
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
		return this.source + " used Sabotage on " + this.target + ", use Nullification?";
	}

	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
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
					game.pushGameController(new RecycleCardsGameController(this.target, Set.of(equipment)));
					game.pushGameController(new UnequipGameController(this.target, equipment.getEquipmentType()));
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
				break;
			case DELAYED:
				this.target.removeDelayed(card);
				game.pushGameController(new RecycleCardsGameController(this.target, Set.of(card)));
				break;
		}
		this.nextStage();
	}
}
