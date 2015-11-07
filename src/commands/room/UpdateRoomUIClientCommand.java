package commands.room;

import java.util.List;

import commands.Command;
import net.Connection;
import net.UserInfo;
import net.client.ClientUI;
import ui.client.RoomGui;

public class UpdateRoomUIClientCommand implements Command<ClientUI> {

	private static final long serialVersionUID = 706856426348903599L;
	private final List<UserInfo> userInfos;
	
	public UpdateRoomUIClientCommand(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	
	@Override
	public void execute(ClientUI ui, Connection connection) {
		RoomGui room = ui.<RoomGui>getPanel().getContent();
		room.updatePlayers(userInfos);
	}

}
