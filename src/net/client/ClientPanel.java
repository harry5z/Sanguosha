package net.client;

import javax.swing.JPanel;

import core.client.game.operations.Operation;

/**
 * @author Harry
 *
 */
public interface ClientPanel<T extends JPanel> {
	
	public T getContent();
	
	public ClientMessageListener getMessageListener();

}
