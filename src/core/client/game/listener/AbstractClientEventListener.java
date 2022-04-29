package core.client.game.listener;

import core.client.game.event.ClientGameEvent;

public abstract class AbstractClientEventListener<T extends ClientGameEvent> implements ClientEventListener<T> {
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
