package core.server;

import java.io.IOException;

import commands.client.WelcomeSessionDisplayClientCommand;
import commands.client.DisplayErrorMessageClientCommand;
import core.server.OnlineUserManager.DuplicatedUserException;
import core.server.OnlineUserManager.UserReconnectionException;
import net.Connection;
import net.ConnectionListener;
import net.server.ServerConnection;
import net.server.ServerEntity;
import utils.Log;

/**
 * This class represents the welcome session (before user login)
 * 
 * @author Harry
 *
 */
public class WelcomeSession implements ServerEntity, ConnectionListener {
	private static final String TAG = "WelcomeSession";

	private final ServerEntity lobby;
	private final OnlineUserManager manager;

	public WelcomeSession() {
		this.lobby = new Lobby();
		this.manager = OnlineUserManager.get();
	}
	
	public void onConnectionReceived(ServerConnection connection) {
		connection.setConnectionListener(this);
		connection.activate();
	}
	
	public void onUserLoggingIn(ServerConnection connection, String name) {
		try {
			manager.login(name, connection, this);
			Log.log(TAG, "User '" + name + "' Logged in");
			connection.send(new WelcomeSessionDisplayClientCommand());
		} catch (DuplicatedUserException e) {
			try {
				connection.sendSynchronously(new DisplayErrorMessageClientCommand(String.format("User \"%s\" exists", name)));
				connection.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (UserReconnectionException e) {
			Log.log(TAG, e.getMessage());
		}
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		Log.error(TAG, "Client connection lost. " + message);
	}

	public void enterLobby(LoggedInUser user) {
		lobby.onUserJoined(user);
	}

	@Override
	public void onUserJoined(LoggedInUser user) {
		// user would not return to welcome session
	}

	@Override
	public void onUserReconnected(LoggedInUser user) {
		// user would not reconnect to welcome session
	}

	@Override
	public void onUserRemoved(LoggedInUser user) {
		// user would not be removed from welcome session
	}

	@Override
	public void onUserDisconnected(LoggedInUser user) {
		manager.logout(user);
	}

}
