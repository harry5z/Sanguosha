package gui.net.server;

import gui.net.ControlButtonGui;
import gui.net.LabelGui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.Connection;
import net.client.ClientMessageListener;
import net.client.ClientPanel;
import commands.welcome.EnterLobbyServerCommand;

public class WelcomeSessionGui extends JPanel implements ClientPanel<WelcomeSessionGui> {
	
	private static final long serialVersionUID = -3932061499692049816L;

	public WelcomeSessionGui(final Connection connection) {
		JLabel label = new LabelGui("Enter Lobby");
		JButton button = new ControlButtonGui("Enter", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connection.send(new EnterLobbyServerCommand());
			}
		});
		setLayout(new GridLayout(0,1));
		add(label);
		add(button);
	}

	@Override
	public WelcomeSessionGui getContent() {
		return this;
	}

	@Override
	public ClientMessageListener getMessageListener() {
		return null;
	}

}
