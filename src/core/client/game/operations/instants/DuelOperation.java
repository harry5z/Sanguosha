package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateDuelInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;

public class DuelOperation extends AbstractSingleTargetCardOperation {

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateDuelInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void setupTargetSelection() {
		GameUI panelUI = this.panel.getGameUI();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			other.setActivatable(true);
		}
		panelUI.setCancelEnabled(true);
	}

}
