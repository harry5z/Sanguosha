package net.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import commands.AggregatedClientCommand;
import commands.Command;
import net.CommandPacket;
import net.Connection;
import net.ConnectionListener;
import utils.Log;

public class ServerConnection extends Connection {
	private static final String TAG = "Server Connection";
	private static final int FLUSH_COMMAND_FREQUENCY_MS = 100;
	
	private final Map<Integer, Command<? super ConnectionListener>> historyCommands;
	private final List<Command<? super ConnectionListener>> commandQueue;
	private int commandCounter;
	private final Timer timer;

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
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processReceivedObject(Object obj) {
		try {
			// Only one command is allowed from client at any time
			// in the future may allow concurrent commands of different types,
			// e.g. chat messages and player actions can happen concurrently
			synchronized (this.accessLock) {
				((Command<? super ConnectionListener>) ((CommandPacket) obj).getCommand()).execute(listener, ServerConnection.this);
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
	@SuppressWarnings("unchecked")
	@Override
	public void send(Command<?> command) {
		synchronized (this.writeLock) {
			this.commandQueue.add((Command<? super ConnectionListener>) command);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void sendSynchronously(Command<?> command) {
		synchronized (this.writeLock) {
			this.commandQueue.add((Command<? super ConnectionListener>) command);
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
			List<Command<? super ConnectionListener>> commandsToSend = List.copyOf(commandQueue);
			commandQueue.clear();
			
			Command<ConnectionListener> command = new AggregatedClientCommand(commandsToSend);
			historyCommands.put(commandCounter, command);
			CommandPacket packet = new CommandPacket(commandCounter, command);
			commandCounter++;
			
			sendCommandPacket(packet);
		}
	}
	
	public void resendCommand(int id) {
		Command<?> command = null;
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
