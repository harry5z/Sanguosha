package core.event.handlers.equipment;

import core.event.game.UnequipEvent;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.HealGameController;
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
		
		if (this.player.isDamaged()) {
			throw new GameFlowInterruptedException(() -> {
				game.pushGameController(new HealGameController(this.player.getPlayerInfo(), this.player.getPlayerInfo(), game));
				game.getGameController().proceed();
			});
		}
	}

}
