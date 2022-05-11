package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.UnequipItemAbilityEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.mechanics.HealGameController;
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
		
		game.pushGameController(new AbstractSingleStageGameController(game) {
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				// remove event handler here because unequip event happens before the heal
				game.removeEventHandler(SilverLionUnequipEventHandler.this);
				if (event.player.isDamaged()) {
					game.pushGameController(new HealGameController(event.player.getPlayerInfo(), event.player.getPlayerInfo(), game));
				};
			}
		});

	}

	@Override
	public Class<UnequipItemAbilityEvent> getEventClass() {
		return UnequipItemAbilityEvent.class;
	}

}
