package commands.client;

import java.util.List;

import core.client.ClientFrame;
import net.UserInfo;
import net.client.ClientConnection;
import ui.client.RoomGui;

public class UpdateRoomUIClientCommand implements ClientCommand {

	private static final long serialVersionUID = 706856426348903599L;
	private final List<UserInfo> userInfos;
	
	public UpdateRoomUIClientCommand(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	
	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		RoomGui room = (RoomGui) ui.getPanel().getPanelUI();
		room.updatePlayers(userInfos);
	}

}
