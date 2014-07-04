package net.server;

import net.Connection;
import utils.Log;

import commands.welcome_commands.WelcomeSessionDisplayClientCommand;

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
		this.lobby = new Lobby();
	}

	@Override
	public boolean onReceivedConnection(Connection connection) {
		connections.add(connection);
		connection.setConnectionListener(this);
		connection.activate();
		connection.send(new WelcomeSessionDisplayClientCommand());
		return true;
	}

	public void enterLobby(Connection connection) {
		synchronized(connection.accessLock) {
			if (connections.contains(connection)) {
				if(lobby.onReceivedConnection(connection))
					connections.remove(connection);
			}
			else
				Log.e(TAG, "Connection does not exist...");
		}
	}

	@Override
	public void onConnectionLost(Connection connection) {
		Log.e(TAG, "Client connection lost");
		connections.remove(connection);
		
	}
}
