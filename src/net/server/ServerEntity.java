package net.server;

import java.util.HashSet;
import java.util.Set;

import core.server.Lobby;
import core.server.Room;
import core.server.WelcomeSession;
import net.Connection;
import net.ConnectionListener;

/**
 * This class represents a generic server-side object, like
 * {@linkplain WelcomeSession}, {@linkplain Lobby}, and 
 * {@linkplain Room}. <br><br>
 * Remember that they are also {@linkplain ConnectionListener}
 * 
 * @author Harry
 *
 */
public abstract class ServerEntity implements ConnectionListener {
	protected final Set<Connection> connections = new HashSet<Connection>();
	
	/**
	 * Receive a connection from another source<br><br>
	 * 
	 * <strong>IMPORTANT</strong>: Remember to also {@linkplain Connection#setConnectionListener(ConnectionListener)}
	 * 
	 * @param connection : connection received
	 * @return true on success, false on failure
	 */
	public abstract boolean onReceivedConnection(Connection connection);
	
	/**
	 * Connection left this entity.
	 * 
	 * @param connection : connection left
	 */
	public abstract void onConnectionLeft(Connection connection);

	

}
