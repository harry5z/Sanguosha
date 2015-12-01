package commands.welcome;

import java.io.IOException;

import net.Connection;
import commands.Command;
import core.client.ClientFrame;

public class WelcomeSessionExitClientCommand implements Command<ClientFrame> {

	private static final long serialVersionUID = -53592037900598522L;

	@Override
	public void execute(ClientFrame object, Connection connection) {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0); // for now...
	}

}
