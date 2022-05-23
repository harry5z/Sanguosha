package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
import core.server.SyncController;

public abstract class ServerInGamePlayerListener {

	protected final String name;
	protected final Set<String> otherNames;
	protected final SyncController controller;
	
	protected ServerInGamePlayerListener(String name, Set<String> allNames, SyncController controller) {
		this.name = name;
		this.controller = controller;
		this.otherNames = allNames.stream().filter(n -> !n.equals(name)).collect(Collectors.toSet());
	}
	
	/**
	 * Send commands to refresh for oneself
	 * 
	 * @param self
	 * @return
	 */
	public abstract void refreshSelf(PlayerCompleteServer self);
	
	/**
	 * Send commands to refresh for another player
	 * 
	 * @param other
	 * @return
	 */
	public abstract void refreshOther(PlayerSimple other);
	
}
