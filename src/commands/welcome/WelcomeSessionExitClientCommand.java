package commands.welcome;

import java.io.IOException;

import net.Connection;
import net.client.ClientUI;

import commands.Command;

public class WelcomeSessionExitClientCommand implements Command<ClientUI> {

	private static final long serialVersionUID = -53592037900598522L;

	@Override
	public void execute(ClientUI object, Connection connection) {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0); // for now...
	}

}
