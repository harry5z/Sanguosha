package net.client;

import gui.net.client.ClientFrameGui;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.Connection;
import net.ConnectionListener;
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

	public void connect() {
		try {
			Connection connection = connector.connect();
			connection.setConnectionListener(this.getClientListener());
			connection.activate();
			Log.log(TAG, "Listening to Server...");
		} catch (IOException e) {
			listener.onConnectionLost(null, "Connection Failed");
		}
	}

	public Connector getConnector() {
		return connector;
	}

	public void registerClientListener(ConnectionListener listener) {
		this.listener = listener;
	}

	public ConnectionListener getClientListener() {
		return listener;
	}

	public static void main(String[] args) {
		Client client = new Client();
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		ClientFrameGui gui = new ClientFrameGui(client);
		client.registerClientListener(gui);
	}

}
