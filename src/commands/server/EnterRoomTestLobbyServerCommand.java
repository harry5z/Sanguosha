package commands.server;

import core.server.Lobby;
import core.server.LoggedInUser;

public class EnterRoomTestLobbyServerCommand extends ServerCommand<Lobby> {

	private static final long serialVersionUID = -1261682255723435476L;

	@Override
	public void execute(Lobby lobby, LoggedInUser user) {
		if (lobby.hasRoom()) {
			lobby.proceedToRoom(0, user); // super hack
		} else {
			lobby.addRoom(null, null, user);
		}
	}

}
