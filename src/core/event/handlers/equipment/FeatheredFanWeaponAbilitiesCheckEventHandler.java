package core.event.handlers.equipment;

import core.event.game.basic.AttackPreAcquisitionWeaponAbilitiesCheckEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Damage.Element;
import core.server.game.Game;
import core.server.game.controllers.equipment.FeatheredFanGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class FeatheredFanWeaponAbilitiesCheckEventHandler extends AbstractEventHandler<AttackPreAcquisitionWeaponAbilitiesCheckEvent> {

	public FeatheredFanWeaponAbilitiesCheckEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackPreAcquisitionWeaponAbilitiesCheckEvent> getEventClass() {
		return AttackPreAcquisitionWeaponAbilitiesCheckEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackPreAcquisitionWeaponAbilitiesCheckEvent event, Game game)
		throws GameFlowInterruptedException {
		if (this.player != event.source) {
			return;
		}
		
		if (event.controller.getAttackCard().getElement() == Element.NORMAL) {
			game.pushGameController(new FeatheredFanGameController(event.source, event.controller));
		}
	}

}
