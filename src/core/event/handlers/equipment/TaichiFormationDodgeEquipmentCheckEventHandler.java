package core.event.handlers.equipment;

import core.event.game.DodgeTargetEquipmentCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.equipment.TaichiFormationGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class TaichiFormationDodgeEquipmentCheckEventHandler extends AbstractEventHandler<DodgeTargetEquipmentCheckEvent> {

	public TaichiFormationDodgeEquipmentCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DodgeTargetEquipmentCheckEvent> getEventClass() {
		return DodgeTargetEquipmentCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(DodgeTargetEquipmentCheckEvent event, Game game) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		game.pushGameController(new TaichiFormationGameController(game, event.controller, this.player));
	}

}
