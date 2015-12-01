package commands.lobby;

import core.server.RoomInfo;
import net.Connection;
import ui.client.LobbyGui;

public class UpdateRoomLobbyUIClientCommand extends LobbyUIClientCommand {

	private static final long serialVersionUID = 3640415584254168416L;

	private final RoomInfo room;
	
	public UpdateRoomLobbyUIClientCommand(RoomInfo room) {
		this.room = room;
	}
	
	@Override
	public void execute(LobbyGui lobby, Connection connection) {
		lobby.updateRoom(room);
	}

}
