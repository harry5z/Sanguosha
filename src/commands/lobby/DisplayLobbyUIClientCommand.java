package commands.lobby;

import java.util.List;

import net.Connection;
import ui.client.LobbyGui;

import commands.Command;
import core.client.ClientFrame;
import core.server.RoomInfo;

/**
 * This Command draws the lobby GUI on client's screen
 * @author Harry
 *
 */
public class DisplayLobbyUIClientCommand implements Command<ClientFrame> {
	private static final long serialVersionUID = 2969055754383503593L;
	private final List<RoomInfo> rooms;
	
	/**
	 * Create lobby's GUI with rooms
	 * @param rooms : rooms currently in the lobby
	 */
	public DisplayLobbyUIClientCommand(List<RoomInfo> rooms) {
		this.rooms = rooms;
	}
	
	@Override
	public void execute(ClientFrame ui, Connection connection) {
		ui.onNewPanelDisplayed(new LobbyGui(rooms, connection));
	}
}