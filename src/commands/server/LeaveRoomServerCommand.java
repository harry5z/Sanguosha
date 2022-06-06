package commands.server;

import core.server.LoggedInUser;
import core.server.Room;

public class LeaveRoomServerCommand extends ServerCommand<Room> {

	private static final long serialVersionUID = 8853765638078143545L;

	@Override
	public void execute(Room room, LoggedInUser user) {
		room.onUserLeft(user);
	}

}
