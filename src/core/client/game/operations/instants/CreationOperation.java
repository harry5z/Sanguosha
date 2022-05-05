package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateCreationInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class CreationOperation extends AbstractCardInitiatedNoTargetOperation {

	public CreationOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateCreationInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use Creation?";
	}

}
