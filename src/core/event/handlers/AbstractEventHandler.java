package core.event.handlers;

import core.event.Event;
import core.player.PlayerCompleteServer;
import core.server.ConnectionController;
import core.server.game.Game;
import exceptions.server.game.GameFlowInterruptedException;

public abstract class AbstractEventHandler<T extends Event> implements EventHandler<T> {

	protected PlayerCompleteServer player;
	private boolean activated;
	
	public AbstractEventHandler(PlayerCompleteServer player) {
		this.player = player;
		this.activated = true;
	}
	
	@Override
	public final PlayerCompleteServer getPlayerSource() {
		return this.player;
	}
	
	@Override
	public final void handle(T event, Game game, ConnectionController connection) throws GameFlowInterruptedException {
		if (this.activated && this.player.isAlive()) {
			this.handleIfActivated(event, game, connection);
		}
	}
	
	protected abstract void handleIfActivated(T event, Game game, ConnectionController connection) throws GameFlowInterruptedException;
	
	@Override
	public final void deactivate() {
		this.activated = false;
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
}
