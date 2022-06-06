package commands.client;

import java.util.List;

import core.client.ClientFrame;
import core.server.RoomInfo;
import net.UserInfo;
import net.client.ClientConnection;
import ui.client.RoomGui;

public class DisplayRoomUIClientCommand implements ClientCommand {
	private static final long serialVersionUID = -5995153324243633984L;
	private final RoomInfo room;
	private final List<UserInfo> userInfos;
	
	public DisplayRoomUIClientCommand(RoomInfo room, List<UserInfo> userInfos) {
		this.room = room;
		this.userInfos = userInfos;
	}
	
	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		ui.onNewPanelDisplayed(new RoomGui(room, connection));
		RoomGui room = (RoomGui) ui.getPanel().getPanelUI();
		room.updatePlayers(userInfos);
	}

}
