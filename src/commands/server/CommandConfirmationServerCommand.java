package commands.server;

import commands.Command;
import net.server.ServerConnection;
import net.server.ServerEntity;

public class CommandConfirmationServerCommand implements Command<ServerEntity, ServerConnection> {

	private static final long serialVersionUID = -1899840279332528392L;
	
	private final int id;
	
	public CommandConfirmationServerCommand(int id) {
		this.id = id;
	}
	
	@Override
	public void execute(ServerEntity object, ServerConnection connection) {
		connection.confirmCommand(id);
	}

}
