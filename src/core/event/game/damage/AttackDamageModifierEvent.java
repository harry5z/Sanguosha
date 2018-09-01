package core.event.game.damage;

import core.server.game.Damage;

public class AttackDamageModifierEvent extends AbstractDamageEvent {
	
	public AttackDamageModifierEvent(Damage damage) {
		super(damage);
	}

}
