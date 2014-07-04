package net.client;

import javax.swing.JPanel;

import net.Message;

/**
 * @author Harry
 *
 */
public interface ClientPanel {
	
	public void onConnectionFailed(String message);
	
	public void onConnectionSuccessful();
	
	public void onMessageReceived(Message message);
	
	JPanel getContent();
}
