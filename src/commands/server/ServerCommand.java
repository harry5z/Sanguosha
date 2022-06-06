package commands.server;

import commands.Command;
import core.server.LoggedInUser;
import net.server.ServerConnection;
import net.server.ServerEntity;

/**
 * This is the general marker interface for commands that are
 * executed on a {@linkplain ServerEntity} over the server side. 
 * 
 * @author Harry
 *
 */
public abstract class ServerCommand<T extends ServerEntity> implements Command<T, ServerConnection> {
	
	private static final long serialVersionUID = 1L;

	@Override
	public final void execute(T object, ServerConnection connection) {
		if (!connection.isLoggedIn()) {
			return;
		}
		execute(object, connection.getUser());
	}
	
	public abstract void execute(T object, LoggedInUser user);
	
}
