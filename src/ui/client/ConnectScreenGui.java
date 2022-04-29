package ui.client;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.client.ClientPanel;
import net.client.Client;
import net.client.ClientMessageListener;
import ui.client.components.ControlButtonGui;
import ui.game.interfaces.ClientGameUI;

public class ConnectScreenGui extends JPanel implements ClientPanel {
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
	public JPanel getUIPanel() {
		return this;
	}

	@Override
	public ClientGameUI getContent() {
		return null;
	}

}
