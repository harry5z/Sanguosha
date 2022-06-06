package core.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import net.server.ServerConnection;
import net.server.ServerEntity;

public class OnlineUserManager {
	
	@SuppressWarnings("serial")
	public static class DuplicatedUserException extends Exception {
		public DuplicatedUserException(String msg) {
			super(msg);
		}
	}
	
	@SuppressWarnings("serial")
	public static class UserReconnectionException extends Exception {
		public UserReconnectionException(String msg) {
			super(msg);
		}
	}
	
	private static class DisconnectedUser {
		
		final LoggedInUser user;
		final TimerTask task;
		
		public DisconnectedUser(LoggedInUser user, TimerTask task) {
			this.user = user;
			this.task = task;
		}
	}
	
	private final Set<LoggedInUser> loggedInUsers;
	private final Map<LoggedInUser, DisconnectedUser> disconnectedUsers;
	private final Timer timer;
	private static final OnlineUserManager MANAGER = new OnlineUserManager();
	
	private static final int USER_DISCONNECTION_TIMEOUT_MS = 300000; // 5 min

	private OnlineUserManager() {
		this.loggedInUsers = new HashSet<>();
		this.disconnectedUsers = new HashMap<>();
		this.timer = new Timer();
	}
	
	public static OnlineUserManager get() {
		return MANAGER;
	}
	
	/**
	 * Log a user in. For now there is no check nor user database, simply a String name
	 * would allow the user to log in. The only requirement is that all names are unique.
	 * 
	 * @param name
	 * @param connection
	 * @throws DuplicatedUserException
	 * @throws UserReconnectionException
	 */
	public synchronized void login(String name, ServerConnection connection, ServerEntity location) throws DuplicatedUserException, UserReconnectionException {
		LoggedInUser user = new LoggedInUser(name);
		if (loggedInUsers.contains(user)) {
			throw new DuplicatedUserException("User \"" + name + "\" exists");
		}
		
		// if user previously disconnected, reconnect now
		DisconnectedUser dcUser = disconnectedUsers.remove(user);
		if (dcUser != null) {
			dcUser.task.cancel();
			dcUser.user.assignConnection(connection);
			connection.assignUser(dcUser.user);
			loggedInUsers.add(dcUser.user);
			ServerEntity lastLocation = dcUser.user.getLocation();
			lastLocation.onUserReconnected(dcUser.user);
			throw new UserReconnectionException("User '" + name + "' reconnected");
		}
		
		connection.assignUser(user);
		user.assignConnection(connection);
		user.moveToLocation(location);
		loggedInUsers.add(user);
	}
	
	public synchronized void logout(LoggedInUser user) {
		loggedInUsers.remove(user);
	}
	
	/**
	 * If a user disconnects while in game, server keeps the last known location of the user. 
	 * If the user reconnects, server attempts to move them back into the game. If game has 
	 * not ended, the user will receive the latest game state to resume playing; if the game 
	 * has ended, user will log in again. Server also considers a user fully disconnected if 
	 * user fails to reconnect within 5 minutes.
	 * 
	 * @param user
	 */
	public synchronized void onInGameUserDisconnected(LoggedInUser user) {
		loggedInUsers.remove(user);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				removeDisconnectedUser(user);
			}
		};
		timer.schedule(task, USER_DISCONNECTION_TIMEOUT_MS);
		disconnectedUsers.put(user, new DisconnectedUser(user, task));
	}
	
	/**
	 * If a game has ended, all disconnected users may not reconnect and need to log in again
	 * 
	 * @param users : disconnected users from a game
	 */
	public synchronized void onGameEnded(Collection<LoggedInUser> users) {
		for (LoggedInUser user : users) {
			DisconnectedUser dcUser = disconnectedUsers.remove(user);
			if (dcUser != null) {
				dcUser.task.cancel();
			}
		}
	}
	
	private synchronized void removeDisconnectedUser(LoggedInUser user) {
		user.getLocation().onUserRemoved(user);
		disconnectedUsers.remove(user);
	}

}
