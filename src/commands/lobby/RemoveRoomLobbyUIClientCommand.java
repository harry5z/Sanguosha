package commands.lobby;

import net.Connection;
import net.server.RoomInfo;
import ui.client.LobbyGui;

public class RemoveRoomLobbyUIClientCommand extends LobbyUIClientCommand {

	private static final long serialVersionUID = -1553637366676111161L;

	private final RoomInfo room;
	
	public RemoveRoomLobbyUIClientCommand(RoomInfo room) {
		this.room = room;
	}
	@Override
	public void execute(LobbyGui lobby, Connection connection) {
		lobby.removeRoom(room);
	}

}
