package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

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
	
}
