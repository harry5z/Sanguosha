package commands.room;

import net.Connection;
import net.server.Room;

public class LeaveRoomServerCommand implements RoomServerCommand {

	private static final long serialVersionUID = 8853765638078143545L;

	@Override
	public void execute(Room room, Connection connection) {
		room.onConnectionLeft(connection);
	}

}
