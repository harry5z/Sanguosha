package core.event.game.damage;

import core.server.game.Damage;

public class TargetEquipmentCheckDamageEvent extends AbstractDamageEvent {

	public TargetEquipmentCheckDamageEvent(Damage damage) {
		super(damage);
	}

}
