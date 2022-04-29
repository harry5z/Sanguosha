package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.ClientGameEvent;

public interface ClientEventListener<E extends ClientGameEvent> {
	
	public Class<E> getEventClass();

	public void handle(E event, GamePanel panel);
	
	public void onDeactivated(GamePanel panel);

}
