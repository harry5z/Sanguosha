package commands.room;

import java.util.List;

import commands.Command;
import core.client.ClientFrame;
import net.Connection;
import net.UserInfo;
import ui.client.RoomGui;

public class UpdateRoomUIClientCommand implements Command<ClientFrame> {

	private static final long serialVersionUID = 706856426348903599L;
	private final List<UserInfo> userInfos;
	
	public UpdateRoomUIClientCommand(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	
	@Override
	public void execute(ClientFrame ui, Connection connection) {
		RoomGui room = (RoomGui) ui.getPanel().getUIPanel();
		room.updatePlayers(userInfos);
	}

}
