package gui.net.client.test;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.Connection;
import net.ConnectionListener;
import net.client.Client;
import net.client.ClientPanel;
import net.client.ClientUI;
import utils.Log;
import commands.lobby.test.EnterRoomTestLobbyServerCommand;
import commands.welcome.EnterLobbyServerCommand;
import core.Constants;

/**
 * A test gui that goes directly into the room
 * 
 * @author Harry
 *
 */
public class ClientFrameTestGui implements ClientUI, ConnectionListener {
	private final JFrame frame;
	private ClientPanel<? extends JPanel> panel;

	public ClientFrameTestGui() {
		this.frame = new JFrame("Sanguosha");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocation(new Random().nextInt(Constants.SCREEN_WIDTH), new Random().nextInt(Constants.SCREEN_HEIGHT));
		frame.setVisible(true);
	}
	
	public synchronized void toRoom(Client client) {
		try {
			Connection connection = client.connect();
			wait();
			connection.send(new EnterLobbyServerCommand());
			wait();
			connection.send(new EnterRoomTestLobbyServerCommand());
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void onNewPanelDisplayed(ClientPanel<? extends JPanel> panel) {
		if (this.panel != null) {
			frame.remove(this.panel.getContent());
		}
		this.panel = panel;
		frame.add(panel.getContent());
		frame.pack();
		notify();
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		Log.error("Client Frame", message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JPanel> ClientPanel<T> getPanel() {
		return (ClientPanel<T>) panel;
	}

}
