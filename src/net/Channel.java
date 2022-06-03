package net;

import commands.Command;

public interface Channel {

	/**
	 * Send command to server / client
	 * 
	 * @param command : the command to be sent
	 */
	public void send(Command<?> command);
	
	/**
	 * Send command to server / client without delay
	 * 
	 * @param command : the command to be sent
	 */
	public void sendSynchronously(Command<?> command);
	
}
