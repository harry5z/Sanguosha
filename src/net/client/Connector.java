package net.client;

import java.io.IOException;
import java.net.Socket;

import net.Connection;
import net.server.Server;
/**
 * Client side class used by each player to connect to master
 * @author Harry
 *
 */
public class Connector
{
	private int serverPort;
	private String serverHost;
	
	/**
	 * Constructor for testing purposes:
	 * <ul>
	 *   <li> port = default port (12345) </li>
	 * 	 <li> host = localhost </li>
	 * </ul>
	 */
	public Connector()
	{
		serverPort = Server.DEFAULT_PORT;
		serverHost = "localhost";
	}

	/**
	 * Set master's port to connect
	 * @param port
	 */
	public void setPort(int port)
	{
		serverPort = port;
	}
	/**
	 * Set master's host to connect
	 * @param host
	 */
	public void setHost(String host)
	{
		serverHost = host;
	}
	/**
	 * Try to connect to game server.
	 * This method should be called only
	 * once.
	 */
	public Connection connect() throws IOException
	{
		Socket socket = new Socket(serverHost,serverPort);
		Connection connection = new ClientConnection(socket);
		return connection;
	}

}
