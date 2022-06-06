package core.client.game.operations.delayed;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.UseLightningInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class LightningOperation extends AbstractCardInitiatedNoTargetOperation {

	public LightningOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new UseLightningInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use Lightning?";
	}

}
