package commands.room;

import core.server.Room;
import net.Connection;

public class LeaveRoomServerCommand implements RoomServerCommand {

	private static final long serialVersionUID = 8853765638078143545L;

	@Override
	public void execute(Room room, Connection connection) {
		room.onConnectionLeft(connection);
	}

}
