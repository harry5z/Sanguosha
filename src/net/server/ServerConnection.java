package net.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import commands.Command;
import net.CommandPacket;
import net.Connection;
import net.ConnectionListener;
import utils.Log;

public class ServerConnection extends Connection {
	private static final String TAG = "Server Connection";
	
	private Map<Integer, Command<?>> historyCommands;
	private int commandCounter;

	public ServerConnection(Socket socket) throws IOException {
		super(socket);
		this.historyCommands = new HashMap<>();
		this.commandCounter = 0;
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
	 * <p>
	 * Server caches each command sent to client so that
	 * if some commands were never received, they can be
	 * sent again
	 * </p>
	 * 
	 */
	@Override
	public void send(Command<?> command) {
		CommandPacket packet = null;
		synchronized (this.writeLock) {
			historyCommands.put(commandCounter, command);
			packet = new CommandPacket(commandCounter, command);
			commandCounter++;
		}
		sendCommandPacket(packet);
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

}
