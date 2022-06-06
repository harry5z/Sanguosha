package commands.server;

import core.server.Lobby;
import core.server.LoggedInUser;

/**
 * This Command lets player enter a room from lobby
 * @author Harry
 *
 */
public class EnterRoomLobbyServerCommand extends ServerCommand<Lobby> {
	private static final long serialVersionUID = 1L;
	/**
	 * id of the room to enter
	 */
	private final int roomID;
	
	/**
	 * Attempts to let user enter the room specified by the id
	 * @param id : room id
	 */
	public EnterRoomLobbyServerCommand(int id) {
		this.roomID = id;
	}
	
	@Override
	public void execute(Lobby lobby, LoggedInUser user) {
		lobby.proceedToRoom(roomID, user);
	}

}
