package core.client.game.operations.delayed;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateStarvationInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;
import utils.DelayedType;

public class StarvationOperation extends AbstractCardInitiatedMultiTargetOperation {

	public StarvationOperation(Activatable source) {
		super(source, 1);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (this.getSelf().equals(player)) {
			return false;
		}
		if (player.hasDelayedType(DelayedType.STARVATION)) {
			return false;
		}
		return this.getSelf().isPlayerInDistance(player, this.panel.getGameState().getNumberOfPlayersAlive());
	}

	@Override
	protected String getMessage() {
		return "Select a target In Distance for Starvation";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateStarvationInGameServerCommand(this.targets.peek().getPlayer().getPlayerInfo(), this.activator.getCard());
	}
}
