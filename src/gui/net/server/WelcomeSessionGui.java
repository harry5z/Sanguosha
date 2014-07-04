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
import net.Message;
import net.client.ClientPanel;
import commands.welcome_commands.EnterLobbyServerCommand;

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
	public void onConnectionFailed(String message) {
		// TODO Auto-generated method stub
		
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
