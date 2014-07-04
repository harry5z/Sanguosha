package gui.net.client;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.Message;
import net.client.Client;
import net.client.ClientUI;
import net.client.ClientPanel;

public class ClientFrameGui implements ClientUI {
	private final JFrame frame;
	private final Client client;
	private ClientPanel panel;

	public ClientFrameGui(Client client) {
		this.client = client;
		this.frame = new JFrame("Sanguosha");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(200, 0);
		frame.setVisible(true);
		this.panel = new ConnectScreenGui(client);
		onNewPanelDisplayed(panel);
	}

	@Override
	public void onConnectionFailed(String message) {
		panel.onConnectionFailed(message);
	}

	@Override
	public void onConnectionSuccessful() {
		panel.onConnectionSuccessful();
	}

	@Override
	public void onNewPanelDisplayed(ClientPanel panel) {
		frame.remove(this.panel.getContent());
		this.panel = panel;
		frame.add(panel.getContent());
		frame.pack();
	}

	@Override
	public void onMessageReceived(Message message) {
		panel.onMessageReceived(message);
	}

	@Override
	public JPanel getContent() {
		return panel.getContent();
	}

}
