package commands.room_commands;

import gui.net.server.RoomGui;
import net.Connection;
import net.client.ClientUI;
import net.server.RoomInfo;
import commands.UIClientCommand;

public class RoomDisplayUIClientCommand implements UIClientCommand {
	private static final long serialVersionUID = -5995153324243633984L;
	private final RoomInfo room;
	
	public RoomDisplayUIClientCommand(RoomInfo room) {
		this.room = room;
	}
	
	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new RoomGui(room, connection));
	}

}
