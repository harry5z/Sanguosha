package commands.server;

import commands.Command;
import net.server.ServerConnection;
import net.server.ServerEntity;

public class RequestResendServerCommand implements Command<ServerEntity, ServerConnection> {
	
	private static final long serialVersionUID = 4675457364123615408L;
	
	private final int id;
	
	public RequestResendServerCommand(int id) {
		this.id = id;
	}

	@Override
	public void execute(ServerEntity object, ServerConnection connection) {
		connection.resendCommand(id);
	}

}
