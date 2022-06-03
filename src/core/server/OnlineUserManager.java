package core.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.Connection;
import net.server.ServerEntity;

public class OnlineUserManager {
	
	public static class LoggedInUser {
		
		private final String name;
		
		public LoggedInUser(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		@Override
		public int hashCode() {
			return name.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof LoggedInUser ? name.equals(((LoggedInUser) obj).name) : false;
		}
	}
	
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
	
	private final Map<LoggedInUser, Connection> loggedInUsers;
	private final Map<Connection, LoggedInUser> connectionMap;
	private final Map<LoggedInUser, ServerEntity> disconnectedUsers;
	private static OnlineUserManager manager;
	
	private OnlineUserManager() {
		this.loggedInUsers = new HashMap<>();
		this.connectionMap = new HashMap<>();
		this.disconnectedUsers = new HashMap<>();
	}
	
	public static OnlineUserManager get() {
		if (manager == null) {
			manager = new OnlineUserManager();
		}
		return manager;
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
	public void login(String name, Connection connection) throws DuplicatedUserException, UserReconnectionException {
		synchronized (this) {
			LoggedInUser user = new LoggedInUser(name);
			if (connectionMap.containsKey(connection)) {
				throw new DuplicatedUserException("May not log in multiple times");
			}
			if (loggedInUsers.containsKey(user)) {
				throw new DuplicatedUserException("User '" + name + "' exists");
			}

			loggedInUsers.put(user, connection);
			connectionMap.put(connection, user);
			if (disconnectedUsers.containsKey(user)) {
				ServerEntity lastLocation = disconnectedUsers.remove(user);
				lastLocation.onUserJoined(connection);
				throw new UserReconnectionException("User '" + name + "' reconnected");
			}
		}
	}
	
	/**
	 * If a user disconnects, it records the last known location of the user. If the user
	 * reconnects, the server attempts to move them into the previous location. If user was 
	 * in game, they will receive the latest game state to resume playing. Note that the 
	 * previous location may no longer exist by the time user reconnects, in which case it
	 * shall be handled correct (e.g. bump user back to lobby)
	 * 
	 * @param connection
	 * @param lastLocation
	 */
	public synchronized void onUserConnectionLost(Connection connection, ServerEntity lastLocation) {
		LoggedInUser user = connectionMap.get(connection);
		loggedInUsers.remove(user);
		connectionMap.remove(connection);
		disconnectedUsers.put(user, lastLocation);
	}
	
	public synchronized void onGameEnded(Collection<Connection> connections) {
		for (Connection connection : connections) {
			LoggedInUser user = connectionMap.get(connection);
			disconnectedUsers.remove(user);
		}
	}
	
	public LoggedInUser getUser(Connection connection) {
		return connectionMap.get(connection);
	}

}
