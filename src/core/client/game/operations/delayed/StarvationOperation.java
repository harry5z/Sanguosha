package core.client.game.operations.delayed;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateStarvationInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;
import utils.DelayedType;

public class StarvationOperation extends AbstractSingleTargetCardOperation {

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateStarvationInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void setupTargetSelection() {
		GameUI panelUI = this.panel.getGameUI();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (this.panel.getGameState().getSelf().isPlayerInDistance(other.getPlayer(), this.panel.getGameState().getNumberOfPlayersAlive()) && !other.getPlayer().hasDelayedType(DelayedType.STARVATION)) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}
}
