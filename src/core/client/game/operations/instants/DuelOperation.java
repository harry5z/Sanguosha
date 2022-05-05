package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateDuelInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

public class DuelOperation extends AbstractCardInitiatedMultiTargetOperation {

	public DuelOperation(Activatable source) {
		super(source, 1);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return !this.getSelf().equals(player);
	}

	@Override
	protected String getMessage() {
		return "Select a target for Duel";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateDuelInGameServerCommand(this.targets.peek().getPlayer().getPlayerInfo(), this.activator.getCard());
	}

}
