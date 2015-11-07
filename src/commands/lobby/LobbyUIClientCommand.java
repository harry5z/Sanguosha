package commands.lobby;

import net.Connection;
import net.client.ClientUI;
import ui.client.LobbyGui;
import commands.Command;

public abstract class LobbyUIClientCommand implements Command<ClientUI> {

	private static final long serialVersionUID = -1525699323874928634L;

	@Override
	public void execute(ClientUI ui, Connection connection) {
		LobbyGui lobby = ui.<LobbyGui>getPanel().getContent();
		this.execute(lobby, connection);
	}
	
	public abstract void execute(LobbyGui lobby, Connection connection);

}
