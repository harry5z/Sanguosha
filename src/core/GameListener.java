package core;

import update.Update;
import net.Client;



public interface GameListener 
{
	/**
	 * Send game update to GUI
	 * @param note
	 */
	public void onNotified(Update note);
	
	/**
	 * Register the client
	 * @param client
	 */
	public void onClientRegistered(Client client);
}
