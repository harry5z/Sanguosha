package ui.client.test;

import java.util.Random;

import javax.swing.JFrame;

import commands.lobby.test.EnterRoomTestLobbyServerCommand;
import commands.welcome.EnterLobbyServerCommand;
import core.Constants;
import core.client.ClientFrame;
import core.client.ClientPanel;
import core.client.ClientPanelUI;
import net.Connection;
import net.ConnectionListener;
import net.client.Client;
import utils.Log;

/**
 * A test gui that goes directly into the room
 * 
 * @author Harry
 *
 */
public class ClientFrameTestGui implements ClientFrame, ConnectionListener {
	private final JFrame frame;
	private ClientPanel<? extends ClientPanelUI> panel;

	public ClientFrameTestGui() {
		this.frame = new JFrame("Sanguosha");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocation(new Random().nextInt(Constants.SCREEN_WIDTH-1000), new Random().nextInt(Constants.SCREEN_HEIGHT-720));
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
	public synchronized void onNewPanelDisplayed(ClientPanel<? extends ClientPanelUI> panel) {
		if (this.panel != null) {
			frame.remove(this.panel.getContent().getPanel());
		}
		this.panel = panel;
		frame.add(panel.getContent().getPanel());
		frame.pack();
		notify();
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		Log.error("Client Frame", message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ClientPanelUI> ClientPanel<T> getPanel() {
		return (ClientPanel<T>) panel;
	}

}
