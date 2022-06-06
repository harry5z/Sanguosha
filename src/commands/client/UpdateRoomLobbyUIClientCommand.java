package commands.client;

import core.server.RoomInfo;
import net.client.ClientConnection;
import ui.client.LobbyGui;

public class UpdateRoomLobbyUIClientCommand extends LobbyUIClientCommand {

	private static final long serialVersionUID = 3640415584254168416L;

	private final RoomInfo room;
	
	public UpdateRoomLobbyUIClientCommand(RoomInfo room) {
		this.room = room;
	}
	
	@Override
	public void execute(LobbyGui lobby, ClientConnection connection) {
		lobby.updateRoom(room);
	}

}
