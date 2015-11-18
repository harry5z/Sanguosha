package commands.game.client;

import core.PlayerInfo;
import core.client.game.operations.DealOperation;
import net.Connection;
import net.client.ClientUI;
import net.client.GamePanel;
import ui.game.GamePanelUI;

public class DealStartGameClientCommmand implements GameClientCommand {

	private static final long serialVersionUID = 5784317669561501939L;

	private final PlayerInfo currentPlayer;
	
	public DealStartGameClientCommmand(PlayerInfo currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	@Override
	public void execute(ClientUI clientUI, Connection connection) {
		synchronized (clientUI) {
			GamePanelUI panelUI = clientUI.<GamePanelUI>getPanel().getContent();
			if (panelUI.getSelf().equals(currentPlayer)) {
				((GamePanel) clientUI.<GamePanelUI>getPanel()).pushOperation(new DealOperation(), null);
			} else {
				panelUI.getOtherPlayerUI(currentPlayer).showCountdownBar();
			}
		}
	}

}
