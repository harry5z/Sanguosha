package core.event.handlers.equipment;

import core.event.game.basic.AttackOnDodgedWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.equipment.DragonBladeGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class DragonBladeAbilitiesCheckEventHandler extends AbstractEventHandler<AttackOnDodgedWeaponAbilitiesCheckEvent> {

	public DragonBladeAbilitiesCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackOnDodgedWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackOnDodgedWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackOnDodgedWeaponAbilitiesCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		game.pushGameController(new DragonBladeGameController(game, event.source, event.target));
	}

}
