package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import utils.Log;
import commands.Command;

/**
 * This class includes information about a single client-server connection. For
 * example, the socket and the streams.
 * 
 * @author Harry
 * 
 */
public class Connection implements Channel {
	private static final String TAG = "Connection";
	/**
	 * Only one thread can send command at a time
	 */
	public final Object writeLock = new Object();
	/**
	 * Only one thread can check on connection at a time
	 */
	public final Object accessLock = new Object();
	private final Socket socket;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;
	private ConnectionListener listener;

	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * Activate this connection to listen for incoming messages. This method
	 * should be called only once.
	 */
	public void activate() {
		new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				while (true) { // evaluation loop
					try {
						final Object obj = in.readObject();
						new Thread(() -> {
							try {
								((Command<? super ConnectionListener>) obj).execute(listener, Connection.this);
							} catch (ClassCastException e) {
								Log.error(TAG, "Command sent to the wrong object: "+e.getMessage());
							}
						}).start();
					} 
					catch (ClassCastException | ClassNotFoundException e) {
						Log.error(TAG, "Received unidentified object");
					}
					catch (IOException e) { // This is fatal
						listener.onConnectionLost(Connection.this, "Unknown IO Exception");
						e.printStackTrace();
						return; // exit eval loop
					}
				}
			}
		}.start();
	}

	public void setConnectionListener(ConnectionListener listener) {
		this.listener = listener;
	}

	/**
	 * <p>{@inheritDoc}</p>
	 * 
	 * This method is <strong>synchronized</strong>
	 * on {@linkplain Connection#writeLock}
	 */
	@Override
	public void send(Command<?> command) {
		synchronized (writeLock) {
			try {
				out.writeUnshared(command);
				out.flush();
			}
			catch (IOException e) {
				Log.error(TAG, "I/O Exception when sending command");
				listener.onConnectionLost(this, "Fail to send command");
			}
		}
	}

	/**
	 * Close the connection. <br>
	 * <b>Note:</b> Server should not close connection unless a fatal error
	 * occurs
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		socket.close();
	}

}