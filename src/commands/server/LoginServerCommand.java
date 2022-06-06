package commands.server;

import commands.Command;
import core.server.WelcomeSession;
import net.server.ServerConnection;

public class LoginServerCommand implements Command<WelcomeSession, ServerConnection> {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	
	public LoginServerCommand(String name) {
		this.name = name;
	}

	@Override
	public void execute(WelcomeSession session, ServerConnection connection) {
		session.onUserLoggingIn(connection, name);
	}

}
