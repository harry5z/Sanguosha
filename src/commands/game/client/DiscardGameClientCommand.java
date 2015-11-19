package commands.game.client;

import core.PlayerInfo;
import core.client.game.operations.DiscardOperation;
import net.Connection;
import net.client.ClientUI;
import net.client.GamePanel;
import ui.game.GamePanelUI;

public class DiscardGameClientCommand implements GameClientCommand {

	private static final long serialVersionUID = 2390690749143332929L;
	
	private final PlayerInfo currentPlayer;
	private final int amount;
	
	public DiscardGameClientCommand(PlayerInfo currentPlayer, int amount) {
		this.currentPlayer = currentPlayer;
		this.amount = amount;
	}
	
	@Override
	public void execute(ClientUI clientUI, Connection connection) {
		GamePanelUI panelUI = clientUI.<GamePanelUI>getPanel().getContent();
		if (panelUI.getSelf().equals(currentPlayer)) {
			((GamePanel) clientUI.<GamePanelUI>getPanel()).pushOperation(new DiscardOperation(amount), null);
		} else {
			panelUI.getOtherPlayerUI(currentPlayer).showCountdownBar();
		}
	}

}
