package net.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import commands.client.ClientCommand;
import commands.client.MessageDisplayClientUICommand;
import core.server.LoggedInUser;
import net.Message;

public class ServerUtils {
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private ServerUtils() {}
	
	/**
	 * Send a command to all connections concurrently
	 * 
	 * @param command : command to send
	 * @param users : users to receive the command
	 */
	public static void sendCommandToUsers(final ClientCommand command, Collection<LoggedInUser> users) {
		List<Callable<Void>> updates = new ArrayList<Callable<Void>>();
		try {
			for (LoggedInUser user : users) {
				updates.add(() -> {
					user.send(command);
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
	
	public void sendChatMessage(Message message, Collection<LoggedInUser> users) {
		ServerUtils.sendCommandToUsers(new MessageDisplayClientUICommand(message) , users);
	}
}
