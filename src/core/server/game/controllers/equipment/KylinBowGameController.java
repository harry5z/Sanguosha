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
	
	public KylinBowGameController(Game game, PlayerCompleteServer source, PlayerCompleteServer target) {
		super(game);
		this.source = source;
		this.target = target;
		this.confirmed = false;
	}
	
	@Override
	protected void handleDecisionRequest() throws GameFlowInterruptedException {
		this.game.emit(new RequestDecisionEvent(this.source.getPlayerInfo(), "Use Icy Sword?"));
		throw new GameFlowInterruptedException();		
	}

	@Override
	protected void handleDecisionConfirmation() throws GameFlowInterruptedException {
		if (!this.confirmed) {
			// skip Action
			this.setStage(PlayerDecisionAction.END);
		}
	}

	@Override
	protected void handleAction() throws GameFlowInterruptedException {
		this.game.emit(new PlayerCardSelectionEvent(
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
	public void onCardSelected(Card card, PlayerCardZone zone) {
		Equipment equipment = (Equipment) card;
		this.game.pushGameController(new RecycleCardsGameController(this.game, this.target, Set.of(equipment)));
		this.game.pushGameController(new UnequipGameController(this.game, this.target, equipment.getEquipmentType()));
	}

}
