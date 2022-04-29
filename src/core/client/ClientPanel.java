package core.client;

import javax.swing.JPanel;

import net.client.ClientMessageListener;
import ui.game.interfaces.ClientGameUI;

/**
 * @author Harry
 *
 */
public interface ClientPanel {
	
	public ClientGameUI getContent();
	
	public JPanel getUIPanel();
	
	public ClientMessageListener getMessageListener();

}
