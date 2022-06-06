package core.server.game.controllers.equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import commands.client.game.equipment.AxeAbilityGameClientCommand;
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
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidPlayerCommandException;

public class AxeGameController extends AbstractPlayerDecisionActionGameController
	implements CardSelectableGameController, DecisionRequiredGameController {
	
	private final PlayerCompleteServer source;
	private final AttackResolutionGameController controller;
	private final List<Card> usedCards;
	private boolean confirmed;

	public AxeGameController(PlayerCompleteServer source, AttackResolutionGameController controller) {
		this.source = source;
		this.controller = controller;
		this.usedCards = new ArrayList<>();
		this.confirmed = false;
	}

	@Override
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		throw new GameFlowInterruptedException(new AxeAbilityGameClientCommand(this.source.getPlayerInfo()));		
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) {
		if (this.confirmed) {
			// if confirmed, force the Attack to hit
			this.controller.setStage(AttackResolutionStage.PRE_DAMAGE_SOURCE_WEAPON_ABILITIES);
			game.log(BattleLog
				.playerAUsedEquipment(source, source.getWeapon())
				.withCards(usedCards)
				.to("make the Attack hit")
			);
		}
	}

	@Override
	protected void handleAction(GameInternal game) {
		// handled in #onCardSelected
	}
	
	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
		this.usedCards.add(card);
		switch (zone) {
			case HAND:
				try {
					// TODO: convert to discard controller
					this.source.discardCard(card);
				} catch (InvalidPlayerCommandException e) {
					e.printStackTrace();
				}
				break;
			case EQUIPMENT:
				Equipment equipment = (Equipment) card;
				game.pushGameController(new RecycleCardsGameController(this.source, Set.of(equipment)));
				game.pushGameController(new UnequipGameController(this.source, equipment.getEquipmentType()));
				break;
			default:
				break;
		}
	}

	@Override
	public void validateCardSelected(GameInternal game, Card card, PlayerCardZone zone) throws IllegalPlayerActionException {
		if (card == null) {
			throw new IllegalPlayerActionException("Axe Reaction: Card cannot be null");
		}
		switch (zone) {
			case HAND:
				if (!source.getCardsOnHand().contains(card)) {
					throw new IllegalPlayerActionException("Axe Reaction: Player does not own the card used");
				}
				break;
			case EQUIPMENT:
				if (!(card instanceof Equipment)) {
					throw new IllegalPlayerActionException("Axe Reaction: Card is not an Equipment");
				}
				if (((Equipment) card).getEquipmentType() == EquipmentType.WEAPON) {
					throw new IllegalPlayerActionException("Axe Reaction: Axe itself cannot be discarded");
				}
				if (!source.isEquippedWith((Equipment) card)) {
					throw new IllegalPlayerActionException("Axe Reaction: Player is not equipped with " + card);
				}
				break;
			default:
				throw new IllegalPlayerActionException("Axe Reaction: Invalid zone");
		}		
	}

}
