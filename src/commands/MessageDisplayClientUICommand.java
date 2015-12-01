package commands;

import core.client.ClientFrame;
import net.Connection;
import net.Message;

public class MessageDisplayClientUICommand implements Command<ClientFrame> {
	private static final long serialVersionUID = -856542896052269531L;
	private final Message message;
	
	public MessageDisplayClientUICommand(Message message) {
		this.message = message;
	}
	
	@Override
	public void execute(ClientFrame ui, Connection connection) {
		ui.getPanel().getMessageListener().onMessageReceived(message);
	}

}
