package commands.client;

import core.client.ClientFrame;
import net.client.ClientConnection;
import ui.client.WelcomeSessionGui;

public class WelcomeSessionDisplayClientCommand implements ClientCommand {
	private static final long serialVersionUID = 4057080358974917775L;

	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		ui.onNewPanelDisplayed(new WelcomeSessionGui(connection));
	}

}
