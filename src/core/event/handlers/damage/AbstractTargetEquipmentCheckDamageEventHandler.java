package core.event.handlers.damage;

import core.event.game.damage.TargetEquipmentCheckDamageEvent;
import core.event.handlers.AbstractEventHandler;
import core.player.PlayerCompleteServer;

public abstract class AbstractTargetEquipmentCheckDamageEventHandler extends AbstractEventHandler<TargetEquipmentCheckDamageEvent> {

	public AbstractTargetEquipmentCheckDamageEventHandler(PlayerCompleteServer player) {
		super(player);
	}

	@Override
	public final Class<TargetEquipmentCheckDamageEvent> getEventClass() {
		return TargetEquipmentCheckDamageEvent.class;
	}

}
