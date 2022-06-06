package core.client.game.operations.instants;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateSabotageInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

public class SabotageOperation extends AbstractCardInitiatedMultiTargetOperation {

	public SabotageOperation(Activatable source) {
		super(source, 1);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (this.getSelf().equals(player)) {
			return false;
		}
		/*
		 * Sabotage can be used on other player if any of it holds true:
		 * 1. the player has cards on hand
		 * 2. the player has equipment
		 * 3. the player has a Delayed Special card
		 */
		return player.getHandCount() > 0 || player.isEquipped() || !player.getDelayedQueue().isEmpty();
	}

	@Override
	protected String getMessage() {
		return "Select a target for Sabotage";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateSabotageInGameServerCommand(this.targets.peek().getPlayer().getPlayerInfo(), this.activator.getCard());
	}

}
