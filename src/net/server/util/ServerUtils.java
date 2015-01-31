package net.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.Connection;
import net.Message;
import commands.Command;
import commands.MessageDisplayClientUICommand;

public class ServerUtils {
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private ServerUtils() {}
	
	/**
	 * Send a command to all connections concurrently
	 * 
	 * @param command : command to send
	 * @param connections : connections to receive the command
	 */
	public static void sendCommandToConnections(final Command<?> command, Collection<Connection> connections) {
		List<Callable<Void>> updates = new ArrayList<Callable<Void>>();
		try {
			for (final Connection connection : connections) {
				updates.add(() -> {
					connection.send(command);
					return null;
				});
			}
			List<Future<Void>> results = EXECUTOR.invokeAll(updates);
			for (Future<Void> result : results)
				result.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void sendChatMessage(Message message, Collection<Connection> connections) {
		ServerUtils.sendCommandToConnections(new MessageDisplayClientUICommand(message) , connections);
	}
}
