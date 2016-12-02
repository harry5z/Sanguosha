package core.event.handlers.damage;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Damage;
import core.server.game.Game;
import core.server.game.Damage.Element;
import exceptions.server.game.GameFlowInterruptedException;

public class RattenArmorCheckDamageEventHandler extends AbstractTargetEquipmentCheckDamageEventHandler {

	public RattenArmorCheckDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(TargetEquipmentCheckDamageEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		Damage damage = event.getDamage();
		if (!this.player.equals(damage.getTarget())) {
			return;
		}
		
		if (damage.getElement() == Element.FIRE) {
			damage.setAmount(damage.getAmount() + 1);
		}
	}

}
