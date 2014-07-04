package gui.net.client;

import gui.net.ControlButtonGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.Message;
import net.client.Client;
import net.client.ClientPanel;

public class ConnectScreenGui extends JPanel implements ClientPanel {
	private static final long serialVersionUID = -948793753026200428L;
	private final Client client;
	private JButton connect;

	public ConnectScreenGui(Client client) {
		this.client = client;
		setLayout(new GridLayout(0, 1));
		this.connect = new ControlButtonGui("Connect", new ActionListener() {
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
		add(connect);
	}

	@Override
	public void onConnectionFailed(String message) {
		connect.setText("Reconnect");
	}

	@Override
	public void onConnectionSuccessful() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageReceived(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getContent() {
		return this;
	}

}
