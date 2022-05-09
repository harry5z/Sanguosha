package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipItemAbilityEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.mechanics.HealGameController;
import core.server.game.controllers.mechanics.UnequipGameController.UnequipStage;
import exceptions.server.game.GameFlowInterruptedException;

public class SilverLionUnequipEventHandler extends AbstractEventHandler<UnequipItemAbilityEvent> {

	public SilverLionUnequipEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(UnequipItemAbilityEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.equals(event.player) || event.equipmentType != EquipmentType.SHIELD) {
			return;
		}
		
		// remove event handler here because unequip event happens before the heal
		game.removeEventHandler(this);
		if (this.player.isDamaged()) {
			throw new GameFlowInterruptedException(() -> {
				event.controller.setStage(UnequipStage.END);
				game.pushGameController(new HealGameController(this.player.getPlayerInfo(), this.player.getPlayerInfo(), game));
				game.getGameController().proceed();
			});
		}
	}

	@Override
	public Class<UnequipItemAbilityEvent> getEventClass() {
		return UnequipItemAbilityEvent.class;
	}

}
