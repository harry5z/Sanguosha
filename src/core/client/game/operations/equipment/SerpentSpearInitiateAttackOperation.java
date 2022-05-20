package core.client.game.operations.equipment;

import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.SerpentSpearInitiateAttackInGameServerCommand;
import core.client.game.operations.AbstractMultiCardMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.EquipmentUI;

public class SerpentSpearInitiateAttackOperation extends AbstractMultiCardMultiTargetOperation {
	
	private final EquipmentUI activator;
	
	public SerpentSpearInitiateAttackOperation(EquipmentUI source) {
		super(2, 1);
		this.activator = source;
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == 2 && this.targets.size() == 1;
	}

	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return false;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return true;
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (this.getSelf().equals(player)) {
			return false;
		}
		return this.getSelf().isPlayerInAttackRange(player, panel.getGameState().getNumberOfPlayersAlive());
	}

	@Override
	protected String getMessage() {
		return "Select 2 cards to use as an Attack on 1 target";
	}

	@Override
	protected void onLoadedCustom() {
		this.activator.setActivatable(true);
		this.activator.setActivated(true);
		// By implementation, this must be the SerpentSpear itself
		// Behave as if CANCEL is clicked
		this.panel.getGameUI().getEquipmentRackUI().setActivatable(Set.of(EquipmentType.WEAPON), equipment -> this.onCanceled());
		
	}

	@Override
	protected void onUnloadedCustom() {
		this.activator.setActivatable(false);
		this.activator.setActivated(false);		
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new SerpentSpearInitiateAttackInGameServerCommand(
			Set.of(this.targets.peek().getPlayer().getPlayerInfo()),
			this.cards.keySet().stream().map(ui -> ui.getCard()).collect(Collectors.toSet())
		);
	}

}
