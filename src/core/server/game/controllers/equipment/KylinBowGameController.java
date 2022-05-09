package core.server.game.controllers.equipment;

import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
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

public class KylinBowGameController extends AbstractStagelessGameController
	implements CardSelectableGameController, DecisionRequiredGameController {

	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private final AttackResolutionGameController controller;
	private Boolean confirmed;
	
	public KylinBowGameController(Game game, PlayerCompleteServer source, PlayerCompleteServer target, AttackResolutionGameController controller) {
		super(game);
		this.source = source;
		this.target = target;
		this.controller = controller;
		this.confirmed = null;
		
	}

	@Override
	public void proceed() {
		if (this.confirmed == null) {
			try {
				this.game.emit(new RequestDecisionEvent(this.source.getPlayerInfo(), "Use Kylin Bow?"));
			} catch (GameFlowInterruptedException e) {
				// won't receive GameFlowInterruptedException
			}
			return;
		} else if (this.confirmed == true) {
			try {
				this.confirmed = false; // enter exit code path
				this.game.emit(new PlayerCardSelectionEvent(
					this.source.getPlayerInfo(),
					this.target.getPlayerInfo(),
					Set.of(PlayerCardZone.EQUIPMENT),
					Set.of(EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS)
				));
			} catch (GameFlowInterruptedException e) {
				// won't receive GameFlowInterruptedException
			}
		} else {
			this.onUnloaded();
			this.game.getGameController().proceed();
		}
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
		this.controller.setStage(AttackResolutionStage.PRE_DAMAGE_SOURCE_WEAPON_DAMAGE_MODIFIERS);
	}

	@Override
	public void onCardSelected(Card card, PlayerCardZone zone) {
		Equipment equipment = (Equipment) card;
		this.game.pushGameController(new RecycleCardsGameController(this.game, this.target, Set.of(equipment)));
		this.game.pushGameController(new UnequipGameController(this.game, this.target, equipment.getEquipmentType()));
	}

}
