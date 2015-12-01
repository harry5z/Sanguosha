package commands.welcome;

import net.Connection;
import ui.client.WelcomeSessionGui;

import commands.Command;
import core.client.ClientFrame;

public class WelcomeSessionDisplayClientCommand implements Command<ClientFrame> {
	private static final long serialVersionUID = 4057080358974917775L;

	@Override
	public void execute(ClientFrame ui, Connection connection) {
		ui.onNewPanelDisplayed(new WelcomeSessionGui(connection));
	}

}
