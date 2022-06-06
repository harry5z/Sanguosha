package core.client.game.operations.equipment;

import java.util.Set;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.SerpentSpearAttackReactionInGameServerCommand;
import core.client.game.operations.AbstractMultiCardMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.EquipmentUI;

public class SerpentSpearAttackReactionOperation extends AbstractMultiCardMultiTargetOperation {
	
	private final EquipmentUI activator;
	
	public SerpentSpearAttackReactionOperation(EquipmentUI source) {
		super(2, 0);
		this.activator = source;
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		return this.cards.size() == 2;
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
		return false;
	}

	@Override
	protected String getMessage() {
		return "Select 2 cards to use as an Attack";
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
		return new SerpentSpearAttackReactionInGameServerCommand(
			this.cards.keySet().stream().map(ui -> ui.getCard()).collect(Collectors.toSet())
		);
	}

}
