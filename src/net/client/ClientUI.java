package net.client;

import javax.swing.JPanel;

public interface ClientUI {

	public void onNewPanelDisplayed(ClientPanel<? extends JPanel> panel);

	public <T extends JPanel> ClientPanel<T> getPanel();

}
