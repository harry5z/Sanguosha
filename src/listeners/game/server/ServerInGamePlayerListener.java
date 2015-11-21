package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import net.server.GameRoom;

public abstract class ServerInGamePlayerListener {

	protected final String name;
	protected final Set<String> otherNames;
	protected final GameRoom room;
	
	protected ServerInGamePlayerListener(String name, Set<String> allNames, GameRoom room) {
		this.name = name;
		this.room = room;
		this.otherNames = allNames.stream().filter(n -> !n.equals(name)).collect(Collectors.toSet());
	}
	
}
