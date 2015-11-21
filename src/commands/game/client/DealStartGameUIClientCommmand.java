package commands.game.client;

import core.PlayerInfo;
import core.client.game.operations.DealOperation;
import net.client.GamePanel;
import ui.game.GamePanelUI;

public class DealStartGameUIClientCommmand extends GameUIClientCommand {

	private static final long serialVersionUID = 5784317669561501939L;

	private final PlayerInfo currentPlayer;
	
	public DealStartGameUIClientCommmand(PlayerInfo currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	@Override
	public void execute(GamePanel panel) {
		GamePanelUI panelUI = panel.getContent();
		if (panelUI.getSelf().getPlayerInfo().equals(currentPlayer)) {
			panel.pushOperation(new DealOperation(), null);
		} else {
			panelUI.getOtherPlayerUI(currentPlayer).showCountdownBar();
		}
	}

}
