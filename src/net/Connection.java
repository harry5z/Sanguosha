package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import utils.Log;

/**
 * This class includes information about a single client-server connection. For
 * example, the socket and the streams.
 * 
 * @author Harry
 * 
 */
public abstract class Connection implements Channel {
	
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
	protected ConnectionListener listener;

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
			@Override
			public void run() {
				while (true) { // evaluation loop
					try {
						final Object obj = in.readUnshared();
						new Thread(() -> processReceivedObject(obj)).start();
					} 
					catch (ClassCastException | ClassNotFoundException e) {
						Log.error(TAG, "Received unidentified object");
					}
					catch (IOException e) { // This is fatal
						listener.onConnectionLost(Connection.this, "Unknown IO Exception");
						onExit();
						return; // exit eval loop
					}
				}
			}
		}.start();
	}
	
	protected abstract void processReceivedObject(Object obj);
	
	protected abstract void onExit();
	
	public void setConnectionListener(ConnectionListener listener) {
		this.listener = listener;
	}

	/**
	 * 
	 * This method is <strong>synchronized</strong>
	 * on {@linkplain Connection#writeLock}
	 * 
	 */
	protected void sendCommandPacket(CommandPacket packet) {
		synchronized (writeLock) {
			try {
				out.writeUnshared(packet);
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