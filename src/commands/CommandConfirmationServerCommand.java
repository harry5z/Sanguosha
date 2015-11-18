package commands;

import net.Connection;
import net.ServerConnection;
import net.server.ServerEntity;

public class CommandConfirmationServerCommand implements Command<ServerEntity> {

	private static final long serialVersionUID = -1899840279332528392L;
	
	private final int id;
	
	public CommandConfirmationServerCommand(int id) {
		this.id = id;
	}
	
	@Override
	public void execute(ServerEntity object, Connection connection) {
		((ServerConnection) connection).confirmCommand(id);
	}

}
