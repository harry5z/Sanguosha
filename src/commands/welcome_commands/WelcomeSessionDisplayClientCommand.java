package commands.welcome_commands;

import gui.net.server.WelcomeSessionGui;
import net.Connection;
import net.client.ClientUI;

import commands.UIClientCommand;

public class WelcomeSessionDisplayClientCommand implements UIClientCommand {
	private static final long serialVersionUID = 4057080358974917775L;

	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new WelcomeSessionGui(connection));
	}

}
