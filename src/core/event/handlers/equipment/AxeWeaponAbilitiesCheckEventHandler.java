package core.event.handlers.equipment;

import core.event.game.basic.AttackOnDodgedWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.equipment.AxeGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class AxeWeaponAbilitiesCheckEventHandler extends AbstractEventHandler<AttackOnDodgedWeaponAbilitiesCheckEvent> {

	public AxeWeaponAbilitiesCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackOnDodgedWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackOnDodgedWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackOnDodgedWeaponAbilitiesCheckEvent event, Game game)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		game.pushGameController(new AxeGameController(event.source, event.controller));
	}

}
