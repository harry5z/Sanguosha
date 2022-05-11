package core.server.game.controllers.equipment;

import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import commands.game.client.equipment.AxeAbilityGameClientCommand;
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
import exceptions.server.game.InvalidPlayerCommandException;

public class AxeGameController extends AbstractPlayerDecisionActionGameController
	implements CardSelectableGameController, DecisionRequiredGameController {
	
	private final PlayerCompleteServer source;
	private final AttackResolutionGameController controller;
	private boolean confirmed;

	public AxeGameController(Game game, PlayerCompleteServer source, AttackResolutionGameController controller) {
		super(game);
		this.source = source;
		this.controller = controller;
		this.confirmed = false;
	}

	@Override
	protected void handleDecisionRequest() throws GameFlowInterruptedException {
		this.game.getConnectionController().sendCommandToAllPlayers(new AxeAbilityGameClientCommand(this.source.getPlayerInfo()));
		throw new GameFlowInterruptedException();		
	}

	@Override
	protected void handleDecisionConfirmation() {
		if (this.confirmed) {
			// if confirmed, force the Attack to hit
			this.controller.setStage(AttackResolutionStage.PRE_DAMAGE_SOURCE_WEAPON_ABILITIES);
		}
	}

	@Override
	protected void handleAction() {
		// handled in #onCardSelected
	}
	
	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone zone) {
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
				this.game.pushGameController(new RecycleCardsGameController(this.game, this.source, Set.of(equipment)));
				this.game.pushGameController(new UnequipGameController(this.game, this.source, equipment.getEquipmentType()));
				break;
			default:
				break;
		}
	}

}
