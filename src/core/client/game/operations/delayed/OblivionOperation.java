package core.client.game.operations.delayed;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateOblivionInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;
import utils.DelayedType;

public class OblivionOperation extends AbstractCardInitiatedMultiTargetOperation {

	public OblivionOperation(Activatable source) {
		super(source, 1);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		return !this.getSelf().equals(player) && !player.hasDelayedType(DelayedType.OBLIVION);
	}

	@Override
	protected String getMessage() {
		return "Select a target to for Oblivion";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateOblivionInGameServerCommand(this.targets.peek().getPlayer().getPlayerInfo(), this.activator.getCard());
	}

}
