package core.event.handlers.equipment;

import core.event.game.damage.AttackDamageModifierEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.BattleLog;
import core.server.game.GameDriver;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class AncientFalchionAttackDamageEventHandler extends AbstractEventHandler<AttackDamageModifierEvent> {

	public AncientFalchionAttackDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackDamageModifierEvent> getEventClass() {
		return AttackDamageModifierEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackDamageModifierEvent event, GameDriver game) throws GameFlowInterruptedException {
		if (this.player != event.getDamage().getSource()) {
			return;
		}
		
		if (event.getDamage().getTarget().getHandCount() == 0) {
			game.pushGameController(new AbstractSingleStageGameController() {
				@Override
				protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
					event.getDamage().setAmount(event.getDamage().getAmount() + 1);
					game.log(BattleLog.playerAEquipmentPassivelyTriggered(player, player.getWeapon(), "Damage <b>+1</b>"));
				}
			});
		}
	}

}
