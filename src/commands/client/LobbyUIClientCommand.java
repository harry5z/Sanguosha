package commands.client;

import core.client.ClientFrame;
import net.client.ClientConnection;
import ui.client.LobbyGui;

public abstract class LobbyUIClientCommand implements ClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		LobbyGui lobby = (LobbyGui) ui.getPanel().getPanelUI();
		this.execute(lobby, connection);
	}
	
	public abstract void execute(LobbyGui lobby, ClientConnection connection);

}
