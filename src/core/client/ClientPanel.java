package core.client;

import javax.swing.JPanel;

import net.client.ClientMessageListener;

/**
 * 
 * A Generic Client-side "Panel" which represents a certain state of the client interface,
 * e.g. Login screen, Lobby, Room, and the actual In-game display.
 * 
 * The Client's main frame will replace the ClientPanel when the client interface enters a 
 * different state, e.g. from Lobby to Room.
 * 
 * @author Harry
 *
 */
public interface ClientPanel {
	
	public JPanel getPanelUI();
	
	public ClientMessageListener getMessageListener();

}
