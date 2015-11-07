package commands.welcome;

import net.Connection;
import net.client.ClientUI;
import ui.client.WelcomeSessionGui;
import commands.Command;

public class WelcomeSessionDisplayClientCommand implements Command<ClientUI> {
	private static final long serialVersionUID = 4057080358974917775L;

	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.onNewPanelDisplayed(new WelcomeSessionGui(connection));
	}

}
