package net.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import commands.Command;
import commands.CommandConfirmationServerCommand;
import commands.RequestResendServerCommand;
import net.CommandPacket;
import net.Connection;
import net.ConnectionListener;
import utils.Log;

public class ClientConnection extends Connection {
	
	private static final String TAG = "Client Connection";
	
	private int currentCommandID;
	private final Set<Integer> receivedCommandIDs;

	public ClientConnection(Socket socket) throws IOException {
		super(socket);
		this.currentCommandID = 0;
		this.receivedCommandIDs = new HashSet<Integer>();
	}

	@Override
	public void send(Command<?> command) {
		sendCommandPacket(new CommandPacket(0, command));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processReceivedObject(Object obj) {
		try {
			CommandPacket packet = (CommandPacket) obj;
			int id = packet.getID();
			Timer t = new Timer();
			synchronized (this) {
				
				// prevent identical commands from executing twice
				if (this.receivedCommandIDs.contains(id)) {
					return;
				}
				this.receivedCommandIDs.add(id);

				// Confirm with server that the command has been received
				sendCommandPacket(new CommandPacket(CommandPacket.CONFIRM, new CommandConfirmationServerCommand(packet.getID())));

				// A rudimentary, half-working lost command recovery mechanism
				// If a future command cannot be executed after a certain timeout
				// e.g. 3s, then it requests ONCE the previous command that's missing
				// TODO build a more robust and efficient recovery mechanism
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						synchronized (ClientConnection.this) {
							if (ClientConnection.this.currentCommandID < id) {
								ClientConnection.this.send(new RequestResendServerCommand(currentCommandID));
							}
						}
					}
					
				}, 3000);
				
				while (id > currentCommandID) {
					wait(); // wait until previous commands are received and executed
				}
				t.cancel(); // lost command recovery no longer needed
			}
			
			// execute the command
			((Command<? super ConnectionListener>) packet.getCommand()).execute(listener, ClientConnection.this);
			
			// this guarantees that all commands are executed in sequential order
			// as they're sent, to prevent newer commands being received and executed by
			// client earlier than older commands
			synchronized (this) {
				currentCommandID++;
				notifyAll();
			}
		} catch (ClassCastException e) {
			Log.error(TAG, "Command sent to the wrong object: " + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			Log.error(TAG, "Error waiting for execution: " + e.getMessage());
			e.printStackTrace();
		}		
	}

	@Override
	protected void onExit() {
		
	}

}
