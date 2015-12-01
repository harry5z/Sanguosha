package commands.lobby;

import net.Connection;
import ui.client.LobbyGui;
import commands.Command;
import core.client.ClientFrame;

public abstract class LobbyUIClientCommand implements Command<ClientFrame> {

	private static final long serialVersionUID = -1525699323874928634L;

	@Override
	public void execute(ClientFrame ui, Connection connection) {
		LobbyGui lobby = ui.<LobbyGui>getPanel().getContent();
		this.execute(lobby, connection);
	}
	
	public abstract void execute(LobbyGui lobby, Connection connection);

}
