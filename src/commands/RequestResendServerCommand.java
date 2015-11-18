package commands;

import net.Connection;
import net.ServerConnection;
import net.server.ServerEntity;

public class RequestResendServerCommand implements Command<ServerEntity> {
	
	private static final long serialVersionUID = 4675457364123615408L;
	
	private final int id;
	
	public RequestResendServerCommand(int id) {
		this.id = id;
	}

	@Override
	public void execute(ServerEntity object, Connection connection) {
		((ServerConnection) connection).resendCommand(id);
	}

}
