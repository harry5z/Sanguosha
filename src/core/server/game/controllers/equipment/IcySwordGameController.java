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
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.GameStateErrorException;
import exceptions.server.game.InvalidPlayerCommandException;

public class IcySwordGameController
	extends AbstractPlayerDecisionActionGameController
	implements CardSelectableGameController, DecisionRequiredGameController {
	

	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private final AttackResolutionGameController controller;
	private int numCardDiscarded;
	private boolean discardCompleted;
	private boolean decisionConfirmed;
	
	public IcySwordGameController(PlayerCompleteServer source, PlayerCompleteServer target, AttackResolutionGameController controller) {
		this.source = source;
		this.target = target;
		this.controller = controller;
		this.numCardDiscarded = 0;
		this.discardCompleted = false;
		this.decisionConfirmed = false;
	}
	

	@Override
	protected void handleDecisionRequest(Game game) throws GameFlowInterruptedException {
		game.emit(new RequestDecisionEvent(this.source.getPlayerInfo(), "Use Icy Sword?"));
		throw new GameFlowInterruptedException();		
	}

	@Override
	protected void handleDecisionConfirmation(Game game) throws GameFlowInterruptedException {
		if (this.decisionConfirmed) {
			// If Icy Sword usage is confirmed, prevent Attack damage
			this.controller.setStage(AttackResolutionStage.END);
		} else {
			// skip Action
			this.setStage(PlayerDecisionAction.END);
		}
	}

	@Override
	protected void handleAction(Game game) throws GameFlowInterruptedException {
		if (!this.discardCompleted) {
			// stay in Action stage while discard is not completed
			this.setStage(PlayerDecisionAction.ACTION);
			game.emit(new PlayerCardSelectionEvent(
				this.source.getPlayerInfo(),
				this.target.getPlayerInfo(),
				Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT)
			));
			throw new GameFlowInterruptedException();
		}		
	}
	
	@Override
	public void onDecisionMade(boolean confirmed) {
		this.decisionConfirmed = confirmed;
	}

	@Override
	public void onCardSelected(Game game, Card card, PlayerCardZone zone) {
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
				game.pushGameController(new RecycleCardsGameController(this.target, Set.of(equipment)));
				game.pushGameController(new UnequipGameController(this.target, equipment.getEquipmentType()));
				break;
			default:
				throw new GameStateErrorException("Icy Sword: Card is not from HAND or EQUIPMENT");
		}
		
		if (this.numCardDiscarded == 2) {
			this.discardCompleted = true;
		}
		
		// exit early if target has no more card to discard
		if (this.target.getHandCount() == 0 && !this.target.isEquipped()) {
			this.discardCompleted = true;
		}
	}

}
