package net.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import commands.Command;
import commands.client.AggregatedClientCommand;
import commands.client.ClientCommand;
import commands.server.CommandConfirmationServerCommand;
import commands.server.LoginServerCommand;
import commands.server.RequestResendServerCommand;
import commands.server.ServerCommand;
import core.server.LoggedInUser;
import core.server.WelcomeSession;
import net.CommandPacket;
import net.Connection;
import utils.Log;

public class ServerConnection extends Connection {
	private static final String TAG = "Server Connection";
	private static final int FLUSH_COMMAND_FREQUENCY_MS = 100;
	
	private final Map<Integer, ClientCommand> historyCommands;
	private final List<ClientCommand> commandQueue;
	private int commandCounter;
	private final Timer timer;
	private LoggedInUser user;

	public ServerConnection(Socket socket) throws IOException {
		super(socket);
		this.historyCommands = new HashMap<>();
		this.commandQueue = new ArrayList<>();
		this.commandCounter = 0;
		this.timer = new Timer();
		// Flush client commands every 0.1s
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				flushCommands();
			}
		}, 0, FLUSH_COMMAND_FREQUENCY_MS);
		this.user = null;
	}
	
	public void assignUser(LoggedInUser user) {
		this.user = user;
	}
	
	public LoggedInUser getUser() {
		return user;
	}
	
	public boolean isLoggedIn() {
		return user != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processReceivedObject(Object obj) {
		try {
			// Only one command is allowed from client at any time
			// in the future may allow concurrent commands of different types,
			// e.g. chat messages and player actions can happen concurrently
			synchronized (this.accessLock) {
				Command<?, ? extends Connection> command = ((CommandPacket) obj).getCommand();
				// User must log in first
				if (!isLoggedIn()) {
					if (command instanceof LoginServerCommand) {
						((LoginServerCommand) command).execute((WelcomeSession) listener, this);
					}
					return;
				}
				
				// TODO clean up these 'instanceof'
				if (command instanceof CommandConfirmationServerCommand) {
					((CommandConfirmationServerCommand) command).execute(null, this);
					return;
				}
				
				if (command instanceof RequestResendServerCommand) {
					((RequestResendServerCommand) command).execute(null, this);
					return;
				}
				
				user.handleClientAction((ServerCommand<ServerEntity>) command);
			}
		} catch (ClassCastException e) {
			Log.error(TAG, "Command sent to the wrong object: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>NOTE: Calling {@link #send(Command)} simply puts the command into a queue. The server periodically
	 * flushes all commands and send them all in an aggregated command. As this method does not send the
	 * actual command over internet, it returns very quickly.</p>
	 * 
	 * <p>
	 * Server caches each command sent to client so that
	 * if some commands were never received, they can be
	 * sent again
	 * </p>
	 * 
	 */
	@Override
	public void send(Command<?, ? extends Connection> command) {
		synchronized (this.writeLock) {
			this.commandQueue.add((ClientCommand) command);
		}
	}
	
	/**
	 * Send command to client without delay
	 * 
	 * @param command : the command to be sent
	 */
	public void sendSynchronously(ClientCommand command) {
		synchronized (this.writeLock) {
			this.commandQueue.add((ClientCommand) command);
			flushCommands();
		}		
	}
	
	/**
	 * Periodically flush commands to be sent to the client.
	 */
	private void flushCommands() {
		synchronized (this.writeLock) {
			if (commandQueue.isEmpty()) { // exit if no queued command to send
				return;
			}
			List<ClientCommand> commandsToSend = List.copyOf(commandQueue);
			commandQueue.clear();
			
			ClientCommand command = new AggregatedClientCommand(commandsToSend);
			historyCommands.put(commandCounter, command);
			CommandPacket packet = new CommandPacket(commandCounter, command);
			commandCounter++;
			
			sendCommandPacket(packet);
		}
	}
	
	public void resendCommand(int id) {
		ClientCommand command = null;
		synchronized (this.writeLock) {
			if (historyCommands.containsKey(id)) {
				command = historyCommands.get(id);
			} else {
				// Technically no an error since it could happen normally
				Log.error(TAG, "command " + id + " no longer exists");
				return;
			}
		}
		sendCommandPacket(new CommandPacket(id, command));
	}
	
	public void confirmCommand(int id) {
		synchronized (this.writeLock) {
			if (historyCommands.containsKey(id)) {
				historyCommands.remove(id);
			}
		}
	}

	@Override
	protected void onExit() {
		this.timer.cancel();
	}

}
