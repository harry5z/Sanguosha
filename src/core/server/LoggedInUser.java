package core.server;


import commands.client.ClientCommand;
import commands.server.ServerCommand;
import net.Connection;
import net.ConnectionListener;
import net.server.ServerEntity;

public class LoggedInUser implements ConnectionListener {
	
	private final String name;
	private Connection connection;
	private ServerEntity location;
	
	public LoggedInUser(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void assignConnection(Connection connection) {
		this.connection = connection;
		this.connection.setConnectionListener(this);
	}
	
	public void moveToLocation(ServerEntity location) {
		this.location = location;
	}
	
	public ServerEntity getLocation() {
		return location;
	}
	
	public void send(ClientCommand command) {
		this.connection.send(command);
	}
	
	public void handleClientAction(ServerCommand<ServerEntity> command) {
		command.execute(location, this);
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		location.onUserDisconnected(this);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof LoggedInUser ? name.equals(((LoggedInUser) obj).name) : false;
	}
}
