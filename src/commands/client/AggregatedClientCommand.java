package commands.client;

import java.util.List;

import core.client.ClientFrame;
import net.client.ClientConnection;

/**
 * This is a special and effectively the ONLY type of command that the client receives. 
 * It aggregates a number of commands to be sent to a user. The purpose of this aggregate
 *  command is to reduce the number of requests.
 *
 */
public class AggregatedClientCommand implements ClientCommand {
	
	private static final long serialVersionUID = 1L;
	
	private final List<ClientCommand> commands;
	
	public AggregatedClientCommand(List<ClientCommand> commands) {
		this.commands = commands;
	}
	
	@Override
	public void execute(ClientFrame ui, ClientConnection connection) {
		for (ClientCommand command : commands) {
			command.execute(ui, connection);
		}
	}

}
