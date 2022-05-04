package core.event.handlers.equipment;

import core.event.game.basic.AttackLockedSourceWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.AttackResolutionGameController.AttackResolutionStage;
import core.server.game.controllers.DamageGameController.DamageStage;
import core.server.game.controllers.DodgeGameController.DodgeStage;
import exceptions.server.game.GameFlowInterruptedException;

public class IronSwordWeaponAbilitiesCheckEventHandler extends AbstractEventHandler<AttackLockedSourceWeaponAbilitiesCheckEvent> {

	public IronSwordWeaponAbilitiesCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackLockedSourceWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackLockedSourceWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackLockedSourceWeaponAbilitiesCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		// Fully ignore all stages which involve checking target equipment
		event.controller.skipStage(AttackResolutionStage.TARGET_LOCKED_TARGET_EQUIPMENT_ABILITIES);
		event.controller.dodgeController.skipStage(DodgeStage.TARGET_EQUIPMENT_ABILITIES);
		event.controller.damageController.skipStage(DamageStage.TARGET_EQUIPMENT_ABILITIES);
	}

}