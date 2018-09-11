package core.event.handlers.equipment;

import core.event.game.basic.AttackPreDamageWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import core.server.game.controllers.equipment.IcySwordGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class IcySwordAbilityCheckEventHandler extends AbstractEventHandler<AttackPreDamageWeaponAbilitiesCheckEvent> {

	public IcySwordAbilityCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackPreDamageWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackPreDamageWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackPreDamageWeaponAbilitiesCheckEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.target.getHandCount() == 0 && !event.target.isEquipped()) {
			return;
		}
		
		throw new GameFlowInterruptedException(() -> {
			game.pushGameController(new IcySwordGameController(game, event.source, event.target, event.controller));
			game.getGameController().proceed();
		});
	}

}
