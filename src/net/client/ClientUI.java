package net.client;

import javax.swing.JPanel;

public interface ClientUI {

	/**
	 * TODO: add check to ensure that this panel is received in the correct order
	 * @param panel
	 */
	public void onNewPanelDisplayed(ClientPanel<? extends JPanel> panel);

	public <T extends JPanel> ClientPanel<T> getPanel();

}
