package commands.client;

import core.client.ClientFrame;
import net.Message;
import net.client.ClientConnection;

public class MessageDisplayClientUICommand implements ClientCommand {
	private static final long serialVersionUID = -856542896052269531L;
	private final Message message;
	
	public MessageDisplayClientUICommand(Message message) {
		this.message = message;
	}
	
	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		ui.getPanel().getMessageListener().onMessageReceived(message);
	}

}
