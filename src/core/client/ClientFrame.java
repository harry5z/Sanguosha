package core.client;

import net.ConnectionListener;

public interface ClientFrame extends ConnectionListener {

	/**
	 * TODO: add check to ensure that this panel is received in the correct order
	 * @param panel
	 */
	void onNewPanelDisplayed(ClientPanel panel);

	public ClientPanel getPanel();

}
