package core.event.handlers.equipment;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.GameDriver;
import exceptions.server.game.GameFlowInterruptedException;

public class SilverLionCheckDamageEventHandler extends AbstractTargetEquipmentCheckDamageEventHandler {

	public SilverLionCheckDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(TargetEquipmentCheckDamageEvent event, GameDriver game) throws GameFlowInterruptedException {
		Damage damage = event.getDamage();
		if (!this.player.equals(damage.getTarget())) {
			return;
		}
		
		if (damage.getAmount() > 1) {
			damage.setAmount(1);
		}
		
	}

}
