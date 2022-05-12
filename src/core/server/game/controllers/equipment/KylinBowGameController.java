package core.server.game.controllers.equipment;

import java.util.Set;

import cards.Card;
import cards.equipments.Equipment;
import cards.equipments.Equipment.EquipmentType;
import core.event.game.basic.RequestDecisionEvent;
import core.event.game.instants.PlayerCardSelectionEvent;
import core.player.PlayerCardZone;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractPlayerDecisionActionGameController;
import core.server.game.controllers.CardSelectableGameController;
import core.server.game.controllers.DecisionRequiredGameController;
import core.server.game.controllers.mechanics.RecycleCardsGameController;
import core.server.game.controllers.mechanics.UnequipGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class KylinBowGameController
	extends AbstractPlayerDecisionActionGameController
	implements CardSelectableGameController, DecisionRequiredGameController {
	
	private final PlayerCompleteServer source;
	private final PlayerCompleteServer target;
	private boolean confirmed;
	
	public KylinBowGameController(PlayerCompleteServer source, PlayerCompleteServer target) {
		this.source = source;
		this.target = target;
		this.confirmed = false;
	}
	
	@Override
	protected void handleDecisionRequest(GameInternal game) throws GameFlowInterruptedException {
		game.emit(new RequestDecisionEvent(this.source.getPlayerInfo(), "Use Icy Sword?"));
		throw new GameFlowInterruptedException();		
	}

	@Override
	protected void handleDecisionConfirmation(GameInternal game) throws GameFlowInterruptedException {
		if (!this.confirmed) {
			// skip Action
			this.setStage(PlayerDecisionAction.END);
		}
	}

	@Override
	protected void handleAction(GameInternal game) throws GameFlowInterruptedException {
		game.emit(new PlayerCardSelectionEvent(
			this.source.getPlayerInfo(),
			this.target.getPlayerInfo(),
			Set.of(PlayerCardZone.EQUIPMENT),
			Set.of(EquipmentType.HORSEPLUS, EquipmentType.HORSEMINUS)
		));
		throw new GameFlowInterruptedException();		
	}

	@Override
	public void onDecisionMade(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public void onCardSelected(GameInternal game, Card card, PlayerCardZone zone) {
		Equipment equipment = (Equipment) card;
		game.pushGameController(new RecycleCardsGameController(this.target, Set.of(equipment)));
		game.pushGameController(new UnequipGameController(this.target, equipment.getEquipmentType()));
	}

}
