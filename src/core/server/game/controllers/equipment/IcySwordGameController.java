package core.server.game.controllers.equipment;

import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.event.game.basic.RequestDecisionEvent;
import core.event.game.instants.PlayerCardSelectionEvent;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractStagelessGameController;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.GameStateErrorException;
import exceptions.server.game.InvalidPlayerCommandException;

public class IcySwordGameController extends AbstractStagelessGameController
	implements CardSelectableGameController, DecisionRequiredGameController {

	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private final AttackResolutionGameController controller;
	private int numCardDiscarded;
	private Boolean confirmed;
	
	public IcySwordGameController(Game game, PlayerCompleteServer source, PlayerCompleteServer target, AttackResolutionGameController controller) {
		super(game);
		this.source = source;
		this.target = target;
		this.controller = controller;
		this.numCardDiscarded = 0;
		this.confirmed = null;
	}

	@Override
	public void proceed() {
		if (this.confirmed == null) {
			try {
				this.game.emit(new RequestDecisionEvent(this.source.getPlayerInfo(), "Use Icy Sword?"));
			} catch (GameFlowInterruptedException e) {
				// won't receive GameFlowInterruptedException
			}
			return;
		} else if (this.confirmed == true) {
			if (this.numCardDiscarded == 2) {
				this.onUnloaded();
				this.game.getGameController().proceed();
			} else {
				try {
					this.game.emit(new PlayerCardSelectionEvent(
						this.source.getPlayerInfo(),
						this.target.getPlayerInfo(),
						Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT)
					));
				} catch (GameFlowInterruptedException e) {
					// won't receive GameFlowInterruptedException
				}
			}
		} else {
			this.onUnloaded();
			this.game.getGameController().proceed();
		}
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
		if (!confirmed) {
			this.controller.setStage(AttackResolutionStage.PRE_DAMAGE_SOURCE_WEAPON_DAMAGE_MODIFIERS);
		} else {
			this.controller.setStage(AttackResolutionStage.END);
		}
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone zone) {
		this.numCardDiscarded++;
		switch(zone) {
			case HAND:
				try {
					// TODO: convert to discard controller
					List<Card> cards = this.target.getCardsOnHand();
					this.target.discardCard(cards.get(new Random().nextInt(cards.size())));
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case EQUIPMENT:
				Equipment equipment = (Equipment) card;
				this.game.pushGameController(new RecycleCardsGameController(this.game, this.target, Set.of(equipment)));
				this.game.pushGameController(new UnequipGameController(this.game, this.target, equipment.getEquipmentType()));
				break;
			default:
				throw new GameStateErrorException("Icy Sword: Card is not from HAND or EQUIPMENT");
		}
		
		// exit early if target has no more card to discard
		if (this.target.getHandCount() == 0 && !this.target.isEquipped()) {
			this.numCardDiscarded = 2;
		}
	}

}
