package core.event.handlers.equipment;

import core.event.game.DodgeTargetEquipmentCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameDriver;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
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
	protected void handleIfActivated(DodgeTargetEquipmentCheckEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (!this.player.getPlayerInfo().equals(event.getTarget())) {
			return;
		}
		
		game.pushGameController(new AbstractSingleStageGameController() {
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				game.pushGameController(new TaichiFormationGameController(event.controller, player));
				game.log(BattleLog.playerAEquipmentPassivelyTriggered(player, player.getShield(), ""));
			}
		});
	}

}
