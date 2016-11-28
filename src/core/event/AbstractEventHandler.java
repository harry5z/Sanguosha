package core.event;

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
		if (this.activated) {
			this.handleIfActivated(event, game, connection);
		}
	}
	
	protected abstract void handleIfActivated(T event, Game game, ConnectionController connection) throws GameFlowInterruptedException;
	
	@Override
	public final void deactivate() {
		this.activated = false;
	}
}
