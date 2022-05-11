package core.event.handlers.hero;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.DodgeTargetEquipmentCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.equipment.TaichiFormationGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class ZhugeliangTaichiDodgeEquipmentCheckEventHandler extends AbstractEventHandler<DodgeTargetEquipmentCheckEvent> {
	
	public ZhugeliangTaichiDodgeEquipmentCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<DodgeTargetEquipmentCheckEvent> getEventClass() {
		return DodgeTargetEquipmentCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(DodgeTargetEquipmentCheckEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		if (this.player.isEquipped(EquipmentType.SHIELD)) {
			return;
		}
		
		game.pushGameController(new TaichiFormationGameController(game, event.controller, this.player));
	}
}
