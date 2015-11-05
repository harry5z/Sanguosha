package commands.room;

import net.Connection;
import net.server.Room;

public class StartGameServerCommand implements RoomServerCommand {
	private static final long serialVersionUID = -1646855974120788771L;

	@Override
	public void execute(Room room, Connection connection) {
		room.startGame();
	}

}
