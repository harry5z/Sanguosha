package core.event.handlers.equipment;

import core.event.game.UnequipEvent;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class SilverLionUnequipEventHandler extends AbstractShieldUnequipEventHandler {

	public SilverLionUnequipEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(UnequipEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.equals(event.player) || this.type != event.equipmentType) {
			return;
		}
		
		if (this.player.getHealthCurrent() < this.player.getHealthLimit()) {
			this.player.changeHealthCurrentBy(1); // TODO push game controller
		}
	}

}
