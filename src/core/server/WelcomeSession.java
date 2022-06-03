package core.server;

import net.Connection;
import net.server.ServerEntity;
import utils.Log;

import java.io.IOException;

import commands.welcome.WelcomeSessionDisplayClientCommand;
import commands.welcome.WelcomeSessionDuplicatedUserCommand;
import commands.welcome.WelcomeSessionExitClientCommand;
import core.server.OnlineUserManager.DuplicatedUserException;
import core.server.OnlineUserManager.UserReconnectionException;

/**
 * This class represents the welcome session (before user login)
 * 
 * @author Harry
 *
 */
public class WelcomeSession extends ServerEntity {
	private static final String TAG = "WelcomeSession";
	private final ServerEntity lobby;
	private final OnlineUserManager manager;

	public WelcomeSession() {
		this.lobby = new Lobby(this);
		this.manager = OnlineUserManager.get();
	}

	@Override
	public boolean onUserJoined(Connection connection) {
		synchronized (this) {
			connections.add(connection);
		}
		connection.setConnectionListener(this);
		connection.activate();
		return true;
	}
	
	public void onUserLoggingIn(Connection connection, String name) {
		try {
			manager.login(name, connection);
			Log.log(TAG, "User '" + name + "' Logged in");
			connection.send(new WelcomeSessionDisplayClientCommand());
		} catch (DuplicatedUserException e) {
			System.err.println(e.getMessage());
			try {
				connection.sendSynchronously(new WelcomeSessionDuplicatedUserCommand(name));
				connection.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (UserReconnectionException e) {
			Log.log(TAG, e.getMessage());
		}
	}
	
	@Override
	public void onConnectionLeft(Connection connection) {
		synchronized (this) {
			if (!connections.contains(connection)) {
				return;
			}
			connections.remove(connection);
			OnlineUserManager.get().logout(connection);
		}
		connection.send(new WelcomeSessionExitClientCommand());
	}

	public void enterLobby(Connection connection) {
		synchronized(this) {
			if (connections.contains(connection)) {
				if(lobby.onUserJoined(connection))
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
			OnlineUserManager.get().logout(connection);
		}
		Log.error(TAG, "Client connection lost. " + message);
	}
}
