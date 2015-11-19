package net.client;

import javax.swing.JPanel;

/**
 * @author Harry
 *
 */
public interface ClientPanel<T extends JPanel> {
	
	public T getContent();
	
	public ClientMessageListener getMessageListener();

}
