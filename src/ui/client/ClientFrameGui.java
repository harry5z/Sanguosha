package ui.client;

import javax.swing.JFrame;

import core.client.ClientFrame;
import core.client.ClientPanel;
import net.Connection;
import net.ConnectionListener;
import net.client.Client;
import utils.Log;

public class ClientFrameGui implements ClientFrame, ConnectionListener {
	private final JFrame frame;
	private ClientPanel panel;

	public ClientFrameGui(Client client) {
		frame = new JFrame("Sanguosha");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(800, 0);
		frame.setVisible(true);
		onNewPanelDisplayed(new ConnectScreenGui(client));
	}

	@Override
	public synchronized void onNewPanelDisplayed(ClientPanel panel) {
		if (this.panel != null) {
			frame.remove(this.panel.getUIPanel());
		}
		this.panel = panel;
		frame.add(panel.getUIPanel());
		frame.pack();
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		Log.error("Client Frame", message);
	}

	@Override
	public ClientPanel getPanel() {
		return panel;
	}

}
