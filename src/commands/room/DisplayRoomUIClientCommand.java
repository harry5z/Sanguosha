package commands.room;

import java.util.List;

import commands.Command;
import core.client.ClientFrame;
import core.server.RoomInfo;
import net.Connection;
import net.UserInfo;
import ui.client.RoomGui;

public class DisplayRoomUIClientCommand implements Command<ClientFrame> {
	private static final long serialVersionUID = -5995153324243633984L;
	private final RoomInfo room;
	private final List<UserInfo> userInfos;
	
	public DisplayRoomUIClientCommand(RoomInfo room, List<UserInfo> userInfos) {
		this.room = room;
		this.userInfos = userInfos;
	}
	
	@Override
	public void execute(ClientFrame ui, Connection connection) {
		ui.onNewPanelDisplayed(new RoomGui(room, connection));
		RoomGui room = (RoomGui) ui.getPanel().getUIPanel();
		room.updatePlayers(userInfos);
	}

}
