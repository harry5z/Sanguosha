package core.client.game.operations.instants;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateFireAttackInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedMultiTargetOperation;
import core.player.PlayerSimple;
import ui.game.interfaces.Activatable;

public class FireAttackOperation extends AbstractCardInitiatedMultiTargetOperation {

	public FireAttackOperation(Activatable source) {
		super(source, 1);
	}

	@Override
	protected boolean isPlayerActivatable(PlayerSimple player) {
		if (this.getSelf().equals(player)) {
			// can use Fire Attack on oneself if more than one card on hand
			return player.getHandCount() > 1;
		} else {
			return player.getHandCount() > 0;
		}
	}

	@Override
	protected String getMessage() {
		return "Select a target (can be yourself) for Fire Attack";
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateFireAttackInGameServerCommand(this.targets.peek().getPlayer().getPlayerInfo(), this.activator.getCard());
	}

}
