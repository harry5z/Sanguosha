package core.client.game.operations.delayed;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateStarvationInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;
import utils.DelayedType;

public class StarvationOperation extends AbstractSingleTargetCardOperation {

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateStarvationInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void setupTargetSelection() {
		ClientGameUI panelUI = this.panel.getContent();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (panelUI.getSelf().isPlayerInDistance(other.getPlayer(), panelUI.getNumberOfPlayersAlive()) && !other.getPlayer().hasDelayedType(DelayedType.STARVATION)) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}
}
