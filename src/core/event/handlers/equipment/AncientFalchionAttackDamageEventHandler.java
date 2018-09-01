package core.event.handlers.equipment;

import core.event.game.damage.AttackDamageModifierEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
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
	protected void handleIfActivated(AttackDamageModifierEvent event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (this.player != event.getDamage().getSource()) {
			return;
		}
		
		if (event.getDamage().getTarget().getHandCount() == 0) {
			event.getDamage().setAmount(event.getDamage().getAmount() + 1);
		}
	}

}
