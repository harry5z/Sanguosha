package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipItemAbilityEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import core.server.game.controllers.mechanics.HealGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class SilverLionUnequipEventHandler extends AbstractEventHandler<UnequipItemAbilityEvent> {

	public SilverLionUnequipEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(UnequipItemAbilityEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (!this.player.equals(event.player) || event.equipmentType != EquipmentType.SHIELD) {
			return;
		}
		
		if (player.isDamaged()) {
			game.pushGameController(new HealGameController(player, player));
		}
	}

	@Override
	public Class<UnequipItemAbilityEvent> getEventClass() {
		return UnequipItemAbilityEvent.class;
	}

}
