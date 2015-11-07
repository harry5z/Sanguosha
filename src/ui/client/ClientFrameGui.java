package ui.client;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.Connection;
import net.ConnectionListener;
import net.client.Client;
import net.client.ClientPanel;
import net.client.ClientUI;
import utils.Log;

public class ClientFrameGui implements ClientUI, ConnectionListener {
	private final JFrame frame;
	private ClientPanel<? extends JPanel> panel;

	public ClientFrameGui(Client client) {
		frame = new JFrame("Sanguosha");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(800, 0);
		frame.setVisible(true);
		onNewPanelDisplayed(new ConnectScreenGui(client));
	}

	@Override
	public void onNewPanelDisplayed(ClientPanel<? extends JPanel> panel) {
		if (panel != null) {
			frame.remove(this.panel.getContent());
		}
		this.panel = panel;
		frame.add(panel.getContent());
		frame.pack();
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
