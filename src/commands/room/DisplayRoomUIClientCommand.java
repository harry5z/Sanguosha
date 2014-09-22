package commands.room;

import gui.net.server.RoomGui;
import net.Connection;
import net.client.ClientUI;
import net.server.RoomInfo;

import commands.Command;

public class DisplayRoomUIClientCommand implements Command<ClientUI> {
	private static final long serialVersionUID = -5995153324243633984L;
	private final RoomInfo room;
	
	public DisplayRoomUIClientCommand(RoomInfo room) {
		this.room = room;
	}
	
	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new RoomGui(room, connection));
	}

}
