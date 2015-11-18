package net;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import commands.Command;
import commands.CommandConfirmationServerCommand;
import commands.RequestResendServerCommand;
import utils.Log;

public class ClientConnection extends Connection {
	
	private static final String TAG = "Client Connection";
	
	private int currentCommandID;

	public ClientConnection(Socket socket) throws IOException {
		super(socket);
		this.currentCommandID = 0;
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
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						synchronized (ClientConnection.this) {
							if (ClientConnection.this.currentCommandID < id) {
								ClientConnection.this.send(new RequestResendServerCommand(currentCommandID));
							}
						}
					}
					
				}, 2000);
				while (id > currentCommandID) {
					wait();
				}
			}
			t.cancel();
			// TODO: what if... currentCommand is executing but timer fired?
			((Command<? super ConnectionListener>) packet.getCommand()).execute(listener, ClientConnection.this);
			sendCommandPacket(new CommandPacket(CommandPacket.CONFIRM, new CommandConfirmationServerCommand(packet.getID())));
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

}
