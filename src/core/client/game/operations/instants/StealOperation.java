package core.client.game.operations.instants;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateStealInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

public class StealOperation extends AbstractCardInitiatedMultiTargetOperation {

	public StealOperation(Activatable source) {
		super(source, 1);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (this.getSelf().equals(player)) {
			return false;
		}
		/*
		 * Sabotage can be used on other player In Distance if any of it holds true:
		 * 1. the player has cards on hand
		 * 2. the player has equipment
		 * 3. the player has a Delayed Special card
		 */
		if (!this.getSelf().isPlayerInDistance(player, this.panel.getGameState().getNumberOfPlayersAlive())) {
			return false;
		}
		return player.getHandCount() > 0 || player.isEquipped() || !player.getDelayedQueue().isEmpty();
	}

	@Override
	protected String getMessage() {
		return "Select a target In Distance for Steal";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateStealInGameServerCommand(this.targets.peek().getPlayer().getPlayerInfo(), this.activator.getCard());
	}

}
