package core.event.handlers;

import core.event.Event;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public interface EventHandler<T extends Event> {
	
	public void handle(T event, Game game, ConnectionController connection) throws GameFlowInterruptedException;
	
	public Class<T> getEventClass();
	
	public PlayerCompleteServer getPlayerSource();
	
	public void deactivate(/* reactivate callback? */);
}
