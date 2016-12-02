package core.event.game.damage;

import core.event.game.AbstractGameEvent;
import core.server.game.Damage;

public abstract class AbstractDamageEvent extends AbstractGameEvent {

	private final Damage damage;
	
	public AbstractDamageEvent(Damage damage) {
		this.damage = damage;
	}
	
	public Damage getDamage() {
		return this.damage;
	}
}
