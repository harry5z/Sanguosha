package gui.net.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.net.ControlButtonGui;
import net.client.Client;
import net.client.ClientMessageListener;
import net.client.ClientPanel;

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
