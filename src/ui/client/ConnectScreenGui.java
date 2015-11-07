package ui.client;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.client.Client;
import net.client.ClientMessageListener;
import net.client.ClientPanel;
import ui.client.components.ControlButtonGui;

public class ConnectScreenGui extends JPanel implements ClientPanel<ConnectScreenGui> {
	private static final long serialVersionUID = -948793753026200428L;
	private JButton connectButton;

	public ConnectScreenGui(Client client) {
		setLayout(new GridLayout(0, 1));
		this.connectButton = new ControlButtonGui("Connect", e -> {
			new Thread(() -> client.connect()).start();
		});
		add(connectButton);
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null;
	}

	@Override
	public ConnectScreenGui getContent() {
		return this;
	}

}
