package commands.server;

import core.server.LoggedInUser;
import core.server.WelcomeSession;

public class EnterLobbyServerCommand extends ServerCommand<WelcomeSession> {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(WelcomeSession session, LoggedInUser user) {
		session.enterLobby(user);
	}
}
