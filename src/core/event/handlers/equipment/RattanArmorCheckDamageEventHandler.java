package core.event.handlers.equipment;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.Damage;
import core.server.game.Damage.Element;
import core.server.game.GameDriver;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class RattanArmorCheckDamageEventHandler extends AbstractTargetEquipmentCheckDamageEventHandler {

	public RattanArmorCheckDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	protected void handleIfActivated(TargetEquipmentCheckDamageEvent event, GameDriver game) throws GameFlowInterruptedException {
		Damage damage = event.getDamage();
		if (!this.player.equals(damage.getTarget())) {
			return;
		}
		
		if (damage.getElement() == Element.FIRE) {
			game.pushGameController(new AbstractSingleStageGameController() {
				@Override
				protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
					damage.setAmount(damage.getAmount() + 1);
					game.log(BattleLog.playerAEquipmentPassivelyTriggered(player, player.getShield(), "Fire damage +1"));
				}
			});
		}
	}

}
