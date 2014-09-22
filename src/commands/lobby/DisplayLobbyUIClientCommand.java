package commands.lobby;

import gui.net.server.LobbyGui;

import java.util.List;

import net.Connection;
import net.client.ClientUI;
import net.server.RoomInfo;

import commands.Command;

/**
 * This Command draws the lobby GUI on client's screen
 * @author Harry
 *
 */
public class DisplayLobbyUIClientCommand implements Command<ClientUI> {
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
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new LobbyGui(rooms, connection));
	}
}