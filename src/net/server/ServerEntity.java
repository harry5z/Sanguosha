package net.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.Connection;
import net.ConnectionListener;
import net.Message;

import commands.ClientCommand;
import commands.MessageDisplayUIClientCommand;

/**
 * This class represents a generic server-side object, like
 * {@linkplain WelcomeSession}, {@linkplain Lobby}, and 
 * {@linkplain Room}. <br><br>
 * Remember that they are also {@linkplain ConnectionListener}
 * 
 * @author Harry
 *
 */
public abstract class ServerEntity implements ConnectionListener {
	private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	protected final Set<Connection> connections = new HashSet<Connection>();
	/**
	 * Receive a connection from another source<br><br>
	 * 
	 * <strong>IMPORTANT</strong>: Remember to also {@linkplain Connection#setConnectionListener(ConnectionListener)}
	 * 
	 * @param connection : connection received
	 * @return true on success, false on failure
	 */
	public abstract boolean onReceivedConnection(Connection connection);
	
	public void onSentChatMessage(Message message) {
		this.sentToAllClients(new MessageDisplayUIClientCommand(message));
	}
	
	public void sentToAllClients(final ClientCommand command) {
		List<Callable<Void>> updates = new ArrayList<Callable<Void>>();
		try {
			for (final Connection connection : connections) {
				updates.add(new Callable<Void>() {
					@Override
					public Void call() throws IOException {
						connection.send(command);
						return null;
					}
				});
			}
			List<Future<Void>> results = executor.invokeAll(updates);
			for (Future<Void> result : results)
				result.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
