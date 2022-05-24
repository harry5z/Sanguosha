package commands.welcome;

import core.server.WelcomeSession;
import net.Connection;

public class LoginServerCommand implements WelcomeSessionServerCommand {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	
	public LoginServerCommand(String name) {
		this.name = name;
	}

	@Override
	public void execute(WelcomeSession session, Connection connection) {
		session.onUserLoggingIn(connection, name);
	}
}
