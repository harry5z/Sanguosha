package ui.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import commands.welcome.EnterLobbyServerCommand;
import core.client.ClientPanel;
import net.Connection;
import net.client.ClientMessageListener;
import ui.client.components.ControlButtonGui;
import ui.client.components.LabelGui;
import ui.game.interfaces.ClientGameUI;

public class WelcomeSessionGui extends JPanel implements ClientPanel {
	
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
	public ClientMessageListener getMessageListener() {
		// TODO display message
		return null;
	}

	@Override
	public ClientGameUI getContent() {
		return null;
	}

	@Override
	public JPanel getUIPanel() {
		return this;
	}

}
