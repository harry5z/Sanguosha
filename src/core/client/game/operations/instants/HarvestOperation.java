package core.client.game.operations.instants;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateHarvestInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class HarvestOperation extends AbstractCardInitiatedNoTargetOperation {

	public HarvestOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateHarvestInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use Harvest?";
	}

}
