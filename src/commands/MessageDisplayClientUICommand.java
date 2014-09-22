package commands;

import net.Connection;
import net.Message;
import net.client.ClientUI;

public class MessageDisplayClientUICommand implements Command<ClientUI> {
	private static final long serialVersionUID = -856542896052269531L;
	private final Message message;
	
	public MessageDisplayClientUICommand(Message message) {
		this.message = message;
	}
	
	@Override
	public void execute(ClientUI ui, Connection connection) {
		ui.getPanel().getMessageListener().onMessageReceived(message);
	}

}
