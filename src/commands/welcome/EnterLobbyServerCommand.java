package commands.welcome;

import net.Connection;
import net.server.WelcomeSession;

public class EnterLobbyServerCommand implements WelcomeSessionServerCommand {
	private static final long serialVersionUID = 3018744020722565195L;

	@Override
	public void execute(WelcomeSession session, Connection connection) {
		session.enterLobby(connection);
	}
}
