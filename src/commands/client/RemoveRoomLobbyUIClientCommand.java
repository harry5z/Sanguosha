package commands.client;

import core.server.RoomInfo;
import net.client.ClientConnection;
import ui.client.LobbyGui;

public class RemoveRoomLobbyUIClientCommand extends LobbyUIClientCommand {

	private static final long serialVersionUID = -1553637366676111161L;

	private final RoomInfo room;
	
	public RemoveRoomLobbyUIClientCommand(RoomInfo room) {
		this.room = room;
	}
	@Override
	public void execute(LobbyGui lobby, ClientConnection connection) {
		lobby.removeRoom(room);
	}

}
