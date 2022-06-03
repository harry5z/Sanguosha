package commands.welcome;

import javax.swing.JOptionPane;

import commands.Command;
import core.client.ClientFrame;
import net.Connection;

public class WelcomeSessionDuplicatedUserCommand implements Command<ClientFrame> {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	
	public WelcomeSessionDuplicatedUserCommand(String name) {
		this.name = name;
	}

	@Override
	public void execute(ClientFrame ui, Connection connection) {
		new Thread(() -> JOptionPane.showMessageDialog(null, String.format("User \"%s\" exists", name))).start();
	}

}
