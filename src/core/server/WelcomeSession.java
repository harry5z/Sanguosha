package core.server;

import net.Connection;
import net.server.ServerEntity;
import utils.Log;

import commands.welcome.WelcomeSessionDisplayClientCommand;
import commands.welcome.WelcomeSessionExitClientCommand;

/**
 * This class represents the welcome session (before user login)
 * 
 * @author Harry
 *
 */
public class WelcomeSession extends ServerEntity {
	private static final String TAG = "WelcomeSession";
	private final ServerEntity lobby;

	public WelcomeSession() {
		this.lobby = new Lobby(this);
	}

	@Override
	public boolean onReceivedConnection(Connection connection) {
		synchronized (this) {
			connections.add(connection);
		}
		connection.setConnectionListener(this);
		connection.activate();
		connection.send(new WelcomeSessionDisplayClientCommand());
		return true;
	}
	
	@Override
	public void onConnectionLeft(Connection connection) {
		synchronized (this) {
			if (!connections.contains(connection)) {
				return;
			}
			connections.remove(connection);
		}
		connection.send(new WelcomeSessionExitClientCommand());
	}

	public void enterLobby(Connection connection) {
		synchronized(this) {
			if (connections.contains(connection)) {
				if(lobby.onReceivedConnection(connection))
					connections.remove(connection);
			}
			else
				Log.error(TAG, "Connection does not exist...");
		}
	}

	@Override
	public void onConnectionLost(Connection connection, String message) {
		synchronized (this) {
			connections.remove(connection);
		}
		Log.error(TAG, "Client connection lost. " + message);
	}
}
