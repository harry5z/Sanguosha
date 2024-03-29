package core.server.game.controllers.equipment;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.client.game.DecisionUIClientCommand;
import commands.client.game.ShowCardSelectionPanelUIClientCommand;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController;
import core.server.game.controllers.mechanics.AttackResolutionGameController.AttackResolutionStage;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.GameStateErrorException;
import exceptions.server.game.IllegalPlayerActionException;
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
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(
			new DecisionUIClientCommand(
				source.getPlayerInfo(),
				"Icy Sword: Use to prevent damage and discard 2 cards from target?"
			)
		);		
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) throws GameFlowInterruptedException {
		if (this.decisionConfirmed) {
			// If Icy Sword usage is confirmed, prevent Attack damage
			this.controller.setStage(AttackResolutionStage.END);
			game.log(BattleLog.playerAUsedEquipment(source, source.getWeapon()).to("discard 2 cards from " + BattleLog.formatPlayer(target)));
		} else {
			// skip Action
			this.setStage(PlayerDecisionAction.END);
		}
	}

	@Override
	protected void handleAction(GameInternal game) throws GameFlowInterruptedException {
		if (!this.discardCompleted) {
			// stay in Action stage while discard is not completed
			this.setStage(PlayerDecisionAction.ACTION);
			throw new GameFlowInterruptedException(
				new ShowCardSelectionPanelUIClientCommand(
					source.getPlayerInfo(),
					target,
					Set.of(PlayerCardZone.HAND, PlayerCardZone.EQUIPMENT),
					Arrays.asList(EquipmentType.values())
				)
			);
		}		
	}
	
	@Override
	public void onDecisionMade(boolean confirmed) {
		this.decisionConfirmed = confirmed;
	}

	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
		this.numCardDiscarded++;
		switch(zone) {
			case HAND:
				try {
					// TODO: convert to discard controller
					List<Card> cards = this.target.getCardsOnHand();
					Card cardToDiscard = cards.get(new Random().nextInt(cards.size()));
					game.log(BattleLog.playerADidXToCards(target, "discarded", List.of(cardToDiscard)));
					this.target.discardCard(cardToDiscard);
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
	
	@Override
	public void validateCardSelected(GameInternal game, Card card, PlayerCardZone zone) throws IllegalPlayerActionException {
		switch (zone) {
			case HAND:
				if (target.getHandCount() == 0) {
					throw new IllegalPlayerActionException("Icy Sword: Target has no card on hand");
				}
				break;
			case EQUIPMENT:
				if (card == null || !(card instanceof Equipment)) {
					throw new IllegalPlayerActionException("Icy Sword: Card is not an Equipment");
				}
				if (!target.isEquippedWith((Equipment) card)) {
					throw new IllegalPlayerActionException("Icy Sword: Target is not equipped with " + card);
				}
				break;
			default:
				throw new IllegalPlayerActionException("Icy Sword: Invalid zone");
		}		
	}

}
