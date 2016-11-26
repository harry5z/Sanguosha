package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.DealOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;
import ui.game.interfaces.ClientGameUI;

public class DealStartGameUIClientCommmand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 5784317669561501939L;

	private final PlayerInfo currentPlayer;
	
	public DealStartGameUIClientCommmand(PlayerInfo currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	@Override
	public void execute(GamePanel<? extends Hero> panel) {
		ClientGameUI<? extends Hero> panelUI = panel.getContent();
		if (panelUI.getSelf().getPlayerInfo().equals(currentPlayer)) {
			panel.pushOperation(new DealOperation());
		} else {
			panelUI.getOtherPlayerUI(currentPlayer).showCountdownBar();
		}
	}

}
