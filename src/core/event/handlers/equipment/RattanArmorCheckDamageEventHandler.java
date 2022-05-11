package core.event.handlers.equipment;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.player.PlayerCompleteServer;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class RattanArmorCheckDamageEventHandler extends AbstractTargetEquipmentCheckDamageEventHandler {

	public RattanArmorCheckDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(TargetEquipmentCheckDamageEvent event, Game game) throws GameFlowInterruptedException {
		Damage damage = event.getDamage();
		if (!this.player.equals(damage.getTarget())) {
			return;
		}
		
		if (damage.getElement() == Element.FIRE) {
			damage.setAmount(damage.getAmount() + 1);
		}
	}

}
