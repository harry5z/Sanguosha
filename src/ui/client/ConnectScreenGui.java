package ui.client;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.client.ClientPanel;
import net.client.Client;
import net.client.ClientMessageListener;
import ui.client.components.ControlButtonGui;

public class ConnectScreenGui extends JPanel implements ClientPanel {
	private static final long serialVersionUID = -948793753026200428L;
	private JButton connectButton;

	public ConnectScreenGui(Client client) {
		setLayout(new GridLayout(0, 1));
		JLabel label = new JLabel("Enter Name", SwingConstants.CENTER);
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		
		JTextField input = new JTextField();
		input.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

		add(label);
		add(input);
		this.connectButton = new ControlButtonGui("Connect", e -> {
			new Thread(() -> {
				if (input.getText() == null || input.getText().equals("")) {
					// TODO limit name length
					JOptionPane.showMessageDialog(null, "Please enter a vaild name");
					return;
				}
				client.setName(input.getText());
				client.connect();
			}).start();
		});
		add(connectButton);
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null;
	}

	@Override
	public JPanel getPanelUI() {
		return this;
	}

}
