package core.client.game.operations.equipment;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import cards.Card;
import cards.equipments.Equipment.EquipmentType;
import commands.game.server.ingame.AxeReactionInGameServerCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.client.game.operations.AbstractMultiCardNoTargetReactionOperation;
import core.player.PlayerCardZone;

public class AxeOperation extends AbstractMultiCardNoTargetReactionOperation {
	
	
	public AxeOperation() {
		super(2);
	}
	
	@Override
	protected boolean isCancelEnabled() {
		return true;
	}

	@Override
	protected boolean isCardActivatable(Card card) {
		return true;
	}
	
	@Override
	protected boolean isEquipmentTypeActivatable(EquipmentType type) {
		return type == EquipmentType.SHIELD || type == EquipmentType.HORSEPLUS || type == EquipmentType.HORSEMINUS;
	}

	@Override
	protected String getMessage() {
		return "Axe: Discard 2 cards to make the Attack hit?";
	}

	@Override
	protected void onLoadedCustom() {
		
	}

	@Override
	protected void onUnloadedCustom() {
		
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		Map<Card, PlayerCardZone> map = this.cards.entrySet().stream().collect(Collectors.toMap(
			entry -> entry.getKey().getCard(),
			entry -> entry.getValue()
		));
		return new AxeReactionInGameServerCommand(map);
	}

	@Override
	protected InGameServerCommand getCommandOnInaction() {
		return new AxeReactionInGameServerCommand(new HashMap<>());
	}
	
}
