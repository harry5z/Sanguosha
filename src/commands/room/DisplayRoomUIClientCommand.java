package commands.room;

import java.util.List;

import commands.Command;
import net.Connection;
import net.UserInfo;
import net.client.ClientUI;
import net.server.RoomInfo;
import ui.client.RoomGui;

public class DisplayRoomUIClientCommand implements Command<ClientUI> {
	private static final long serialVersionUID = -5995153324243633984L;
	private final RoomInfo room;
	private final List<UserInfo> userInfos;
	
	public DisplayRoomUIClientCommand(RoomInfo room, List<UserInfo> userInfos) {
		this.room = room;
		this.userInfos = userInfos;
	}
	
	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new RoomGui(room, connection));
		RoomGui room = ui.<RoomGui>getPanel().getContent();
		room.updatePlayers(userInfos);
	}

}
