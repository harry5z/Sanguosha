package net.server;

import core.server.Lobby;
import core.server.LoggedInUser;
import core.server.Room;
import core.server.WelcomeSession;

/**
 * This class represents a generic server-side object, like
 * {@linkplain WelcomeSession}, {@linkplain Lobby}, and 
 * {@linkplain Room}.
 * 
 * @author Harry
 *
 */
public interface ServerEntity {

	/**
	 * When a user enters
	 * 
	 * @param user
	 */
	public void onUserJoined(LoggedInUser user);
	
	/**
	 * When a user disconnects due to either user action or internet disconnection
	 * 
	 * @param user
	 */
	public void onUserDisconnected(LoggedInUser user);
	
	/**
	 * When a user reconnects from disconnection
	 * 
	 * @param user
	 */
	public void onUserReconnected(LoggedInUser user);
	
	/**
	 * When a user is removed from server due to failure to reconnect after a certain disconnection timeout
	 * @param user
	 */
	public void onUserRemoved(LoggedInUser user);

}
