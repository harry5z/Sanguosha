package commands.lobby_commands;

import net.Connection;
import net.server.Lobby;

/**
 * This Command lets player enter a room from lobby
 * @author Harry
 *
 */
public class EnterRoomServerCommand implements LobbyServerCommand {
	private static final long serialVersionUID = 2647446625860059095L;
	/**
	 * id of the room to enter
	 */
	private final int roomID;
	
	/**
	 * Attempts to let user enter the room specified by the id
	 * @param id : room id
	 */
	public EnterRoomServerCommand(int id) {
		this.roomID = id;
	}
	
	@Override
	public void execute(Lobby lobby, Connection connection) {
		lobby.proceedToRoom(roomID, connection);
	}

}
