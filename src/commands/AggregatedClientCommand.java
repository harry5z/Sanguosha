package commands;

import java.util.List;

import net.Connection;
import net.ConnectionListener;

/**
 * This is a special and effectively the ONLY type of command that the client receives. 
 * It aggregates a number of commands to be sent to a user. The purpose of this aggregate
 *  command is to reduce the number of requests.
 *
 */
public class AggregatedClientCommand implements Command<ConnectionListener> {
	
	private static final long serialVersionUID = 1L;
	
	private final List<Command<? super ConnectionListener>> commands;
	
	public AggregatedClientCommand(List<Command<? super ConnectionListener>> commands) {
		this.commands = commands;
	}
	
	@Override
	public void execute(ConnectionListener ui, Connection connection) {
		for (Command<? super ConnectionListener> command : commands) {
			command.execute(ui, connection);
		}
	}

}
