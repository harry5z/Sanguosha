package commands.client;

import javax.swing.JOptionPane;

import core.client.ClientFrame;
import net.client.ClientConnection;

public class DisplayErrorMessageClientCommand implements ClientCommand {
	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public DisplayErrorMessageClientCommand(String message) {
		this.message = message;
	}

	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		new Thread(() -> JOptionPane.showMessageDialog(null, message)).start();
	}

}
