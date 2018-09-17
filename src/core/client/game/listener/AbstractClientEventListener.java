package core.client.game.listener;

import core.client.game.event.ClientGameEvent;
import core.heroes.Hero;

public abstract class AbstractClientEventListener<T extends ClientGameEvent> implements ClientEventListener<T, Hero> {
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
