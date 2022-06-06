package core.client.game.operations.instants;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateArrowSalvoInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class ArrowSalvoOperation extends AbstractCardInitiatedNoTargetOperation {

	public ArrowSalvoOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateArrowSalvoInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use ArrowSalvo?";
	}

}
