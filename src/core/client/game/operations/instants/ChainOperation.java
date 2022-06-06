package core.client.game.operations.instants;

import java.util.LinkedList;
import java.util.Queue;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateChainInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerInfo;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.PlayerUI;

public class ChainOperation extends AbstractCardInitiatedMultiTargetOperation {

	public ChainOperation(Activatable activator) {
		super(activator, 2);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return true;
	}
	
	@Override
	protected boolean isConfirmEnabled() {
		// Chain can be used with 0, 1, or 2 targets
		return true;
	}

	@Override
	protected String getMessage() {
		return "Select 0, 1, or 2 targets for Chain";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		Queue<PlayerInfo> queue = new LinkedList<>();
		for (PlayerUI player : this.targets) {
			queue.add(player.getPlayer().getPlayerInfo());
		}
		return new InitiateChainInGameServerCommand(queue, this.activator.getCard());
	}

}
