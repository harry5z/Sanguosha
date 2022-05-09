package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.ClientGameEvent;

public interface ClientEventListener<E extends ClientGameEvent> {
	
	public Class<E> getEventClass();

	/**
	 * handle the event when the calling Operation is loaded. For example, enable usable skills or equipment
	 * @param event
	 * @param panel
	 */
	public void handleOnLoaded(E event, GamePanel panel);
	
	/**
	 * handle the event when the calling Operation is unloaded. For example, disable the enabled skill or equipment
	 * @param panel
	 */
	public void handleOnUnloaded(GamePanel panel);
}
