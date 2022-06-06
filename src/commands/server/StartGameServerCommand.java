package commands.server;

import core.server.LoggedInUser;
import core.server.Room;

public class StartGameServerCommand extends ServerCommand<Room> {
	private static final long serialVersionUID = -1646855974120788771L;

	@Override
	public void execute(Room room, LoggedInUser user) {
		room.startGame();
	}

}
