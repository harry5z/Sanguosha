package net.client;

import java.io.IOException;
import java.util.Arrays;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.Connection;
import net.ConnectionListener;
import ui.client.ClientFrameGui;
import ui.client.test.ClientFrameTestGui;
import utils.Log;

/**
 * A representation of Client
 * 
 * @author Harry
 * 
 */
public class Client {
	private static final String TAG = "Client";
	private final Connector connector;
	private ConnectionListener listener;

	public Client() {
		this.connector = new Connector();
	}

	/**
	 * Try to connect to game server
	 * 
	 * @return the connection object if successful, null if failed
	 */
	public Connection connect() {
		try {
		    Connection connection = connector.connect();
			connection.setConnectionListener(this.getClientListener());
			connection.activate();
			Log.log(TAG, "Listening to Server...");
			return connection;
		} catch (IOException e) {
			listener.onConnectionLost(null, "Connection Failed");
			return null;
		}
	}

	public void registerClientListener(ConnectionListener listener) {
		this.listener = listener;
	}

	public ConnectionListener getClientListener() {
		return listener;
	}

	public static void main(String[] args) {
		Client client = new Client();
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            try {
						UIManager.setLookAndFeel(info.getClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
		            break;
		        }
		    }
		if (Arrays.asList(args).contains("test")) {
			ClientFrameTestGui gui = new ClientFrameTestGui();
			client.registerClientListener(gui);
			gui.toRoom(client);
		} else {
			ClientFrameGui gui = new ClientFrameGui(client);
			client.registerClientListener(gui);
		}
	}

}
