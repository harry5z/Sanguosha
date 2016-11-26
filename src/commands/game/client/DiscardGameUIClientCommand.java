package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.DiscardOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;
import ui.game.interfaces.ClientGameUI;

public class DiscardGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 2390690749143332929L;
	
	private final PlayerInfo currentPlayer;
	private final int amount;
	
	public DiscardGameUIClientCommand(PlayerInfo currentPlayer, int amount) {
		this.currentPlayer = currentPlayer;
		this.amount = amount;
	}
	
	@Override
	public void execute(GamePanel<? extends Hero> panel) {
		ClientGameUI<? extends Hero> panelUI = panel.getContent();
		if (panelUI.getSelf().getPlayerInfo().equals(currentPlayer)) {
			panel.pushOperation(new DiscardOperation(amount));
		} else {
			panelUI.getOtherPlayerUI(currentPlayer).showCountdownBar();
		}
	}

}
