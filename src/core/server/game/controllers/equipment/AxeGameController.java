package core.server.game.controllers.equipment;

import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.AbstractGameController;
import core.server.game.controllers.AttackResolutionGameController;
import core.server.game.controllers.AttackResolutionGameController.AttackResolutionStage;
import core.server.game.controllers.RecycleCardsGameController;
import core.server.game.controllers.UnequipGameController;
import core.server.game.controllers.interfaces.CardSelectableGameController;
import core.server.game.controllers.interfaces.DecisionRequiredGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class AxeGameController
	extends AbstractGameController
	implements CardSelectableGameController, DecisionRequiredGameController {
	
	private final PlayerCompleteServer source;
	private final AttackResolutionGameController controller;

	public AxeGameController(Game game, PlayerCompleteServer source, AttackResolutionGameController controller) {
		super(game);
		this.source = source;
		this.controller = controller;
	}

	@Override
	public void proceed() {
		this.onUnloaded();
		this.game.getGameController().proceed();
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		if (confirmed) {
			this.controller.setStage(AttackResolutionStage.PRE_DAMAGE_WEAPON_ABILITIES);
		} else {
			this.controller.setStage(AttackResolutionStage.END);
		}
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
				this.game.pushGameController(
					new UnequipGameController(this.game, this.source, equipment.getEquipmentType())
						.setNextController(new RecycleCardsGameController(this.game, this.source, Set.of(equipment)))
				);
				break;
			default:
				break;
		}
	}

}
