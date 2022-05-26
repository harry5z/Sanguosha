package core.event.game.damage;

import core.server.game.Damage;

public class SourceHeroOnDamageEvent extends AbstractDamageEvent {

	public SourceHeroOnDamageEvent(Damage damage) {
		super(damage);
	}

}
