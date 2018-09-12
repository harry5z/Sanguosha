package core.event.handlers.equipment;

import cards.equipments.Equipment.EquipmentType;
import core.event.game.basic.AttackEndEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public class IronSwordTargetShieldResetEventHandler extends AbstractEventHandler<AttackEndEvent> {
	
	public IronSwordTargetShieldResetEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public Class<AttackEndEvent> getEventClass() {
		return AttackEndEvent.class;
	}

	@Override
	protected void handleIfActivated(AttackEndEvent event, Game game, ConnectionController connection)
		throws GameFlowInterruptedException {
		if (event.target.isEquipped(EquipmentType.SHIELD)) {
			// reactivate target shield abilities (if still present)
			event.target.getShield().onActivated(game, event.target);
		}
		game.removeEventHandler(this);
	}

}
