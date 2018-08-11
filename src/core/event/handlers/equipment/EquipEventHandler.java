package core.event.handlers.equipment;

import core.event.game.EquipEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class EquipEventHandler extends AbstractEventHandler<EquipEvent> {

	public EquipEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<EquipEvent> getEventClass() {
		return EquipEvent.class;
	}

	@Override
	protected void handleIfActivated(EquipEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.equals(event.player)) {
			return;
		}
		try {
			this.player.equip(event.equipment);
			event.equipment.onEquipped(game, player);
		} catch (InvalidPlayerCommandException e) {
			// Should not happen
			throw new RuntimeException("cannot equip");
		}
	}

}
