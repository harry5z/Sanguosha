package core.client.game.listener;

import core.client.GamePanel;
import core.client.game.event.ClientGameEvent;
import core.heroes.Hero;

public interface ClientEventListener<E extends ClientGameEvent, H extends Hero> {
	
	public Class<E> getEventClass();

	public void handle(E event, GamePanel<H> panel);

}
