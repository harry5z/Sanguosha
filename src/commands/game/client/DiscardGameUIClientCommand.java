package commands.game.client;

import core.PlayerInfo;
import core.client.game.operations.DiscardOperation;
import net.client.GamePanel;
import ui.game.GamePanelUI;

public class DiscardGameUIClientCommand extends GameUIClientCommand {

	private static final long serialVersionUID = 2390690749143332929L;
	
	private final PlayerInfo currentPlayer;
	private final int amount;
	
	public DiscardGameUIClientCommand(PlayerInfo currentPlayer, int amount) {
		this.currentPlayer = currentPlayer;
		this.amount = amount;
	}
	
	@Override
	public void execute(GamePanel panel) {
		GamePanelUI panelUI = panel.getContent();
		if (panelUI.getSelf().equals(currentPlayer)) {
			panel.pushOperation(new DiscardOperation(amount), null);
		} else {
			panelUI.getOtherPlayerUI(currentPlayer).showCountdownBar();
		}
	}

}
