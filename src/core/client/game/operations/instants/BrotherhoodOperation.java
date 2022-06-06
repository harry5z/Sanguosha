package core.client.game.operations.instants;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.InitiateBrotherhoodInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class BrotherhoodOperation extends AbstractCardInitiatedNoTargetOperation {

	public BrotherhoodOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateBrotherhoodInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use Brotherhoold?";
	}

}
