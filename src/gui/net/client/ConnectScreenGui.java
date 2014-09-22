package gui.net.client;

import gui.net.ControlButtonGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.client.Client;
import net.client.ClientMessageListener;
import net.client.ClientPanel;

public class ConnectScreenGui extends JPanel implements ClientPanel<ConnectScreenGui> {
	private static final long serialVersionUID = -948793753026200428L;
	private JButton connectButton;

	public ConnectScreenGui(Client client) {
		setLayout(new GridLayout(0, 1));
		this.connectButton = new ControlButtonGui("Connect", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						client.connect();
					}
				}).start();
			}
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
