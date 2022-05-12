package core.event.handlers;

import core.event.Event;
import core.player.PlayerCompleteServer;
import core.server.game.GameDriver;
import exceptions.server.game.GameFlowInterruptedException;

public interface EventHandler<T extends Event> {
	
	public void handle(T event, GameDriver game) throws GameFlowInterruptedException;
	
	public Class<T> getEventClass();
	
	public PlayerCompleteServer getPlayerSource();
	
	public void deactivate(/* reactivate callback? */);
}
