package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateStealInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;

public class StealOperation extends AbstractSingleTargetCardOperation {

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateStealInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void setupTargetSelection() {
		ClientGameUI panelUI = this.panel.getContent();
		int numPlayersAlive = panelUI.getNumberOfPlayersAlive();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			PlayerSimple otherPlayer = other.getPlayer();
			if (
				panelUI.getSelf().isPlayerInDistance(otherPlayer, numPlayersAlive) &&
				(otherPlayer.getHandCount() > 0 || otherPlayer.isEquipped() || !otherPlayer.getDelayedQueue().isEmpty())
			) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
