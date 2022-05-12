package core.server.game;

import core.event.game.GameEvent;
import core.event.handlers.EventHandler;

public interface GameEventRegistrar {
	
	public <T extends GameEvent> void registerEventHandler(EventHandler<T> handler);
	
	public <T extends GameEvent> void removeEventHandler(EventHandler<T> handler);
}
