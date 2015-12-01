package net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import core.server.WelcomeSession;
import net.Connection;
import utils.Log;

/**
 * master side, used by framework to communicate with all players
 * 
 * @author Harry
 * 
 */
public class Server {
	public static final int DEFAULT_PORT = 12345;
	private static final String TAG = "Server";
	private final int mPort;
	private final ExecutorService executor;

	private WelcomeSession session;

	public Server(int port) {
		session = new WelcomeSession();
		mPort = port;
		executor = Executors.newCachedThreadPool();
	}

	/**
	 * initialize server with DEFAULT_PORT
	 */
	public Server() {
		session = new WelcomeSession();
		mPort = DEFAULT_PORT;
		executor = Executors.newCachedThreadPool();
	}

	/**
	 * Start the server
	 */
	public void start() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(mPort);
			Log.log(TAG, "waiting for connections...");
			while (true) {
				final Socket socket = serverSocket.accept();
				executor.execute(() -> {
					Log.log(TAG, "receiving new connection...");
					try {
						Connection connection = new ServerConnection(socket);
						session.onReceivedConnection(connection);
					} 
					catch (IOException e) {
						Log.error(TAG, "I/O Exception");
						e.printStackTrace();
					}
				});
			}
		} 
		catch (IOException e) {
			Log.error(TAG, "error creating server socket");
			e.printStackTrace();
		} 
		catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server().start();
	}
}
