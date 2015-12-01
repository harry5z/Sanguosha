package commands.room;

import core.server.Room;
import net.Connection;

public class StartGameServerCommand implements RoomServerCommand {
	private static final long serialVersionUID = -1646855974120788771L;

	@Override
	public void execute(Room room, Connection connection) {
		room.startGame();
	}

}
