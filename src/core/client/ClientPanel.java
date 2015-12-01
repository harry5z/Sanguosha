package core.client;

import net.client.ClientMessageListener;

/**
 * @author Harry
 *
 */
public interface ClientPanel<T extends ClientPanelUI> {
	
	public T getContent();
	
	public ClientMessageListener getMessageListener();

}
