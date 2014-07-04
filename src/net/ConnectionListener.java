package net;


/**
 * This interface represents a server-side object that
 * is responsible for monitoring the status of a connection
 * 
 * @author Harry
 *
 */
public interface ConnectionListener 
{
	/**
	 * Listener must deal with the situation when
	 * client connection is lost
	 * 
	 * @param connection : the broken connection
	 */
	public void onConnectionLost(Connection connection);
}
