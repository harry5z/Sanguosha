package commands.client;

import java.util.List;

import core.client.ClientFrame;
import core.server.RoomInfo;
import net.client.ClientConnection;
import ui.client.LobbyGui;

/**
 * This Command draws the lobby GUI on client's screen
 * @author Harry
 *
 */
public class DisplayLobbyUIClientCommand implements ClientCommand {
	private static final long serialVersionUID = 1L;
	private final List<RoomInfo> rooms;
	
	/**
	 * Create lobby's GUI with rooms
	 * @param rooms : rooms currently in the lobby
	 */
	public DisplayLobbyUIClientCommand(List<RoomInfo> rooms) {
		this.rooms = rooms;
	}
	
	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		ui.onNewPanelDisplayed(new LobbyGui(rooms, connection));
	}
}