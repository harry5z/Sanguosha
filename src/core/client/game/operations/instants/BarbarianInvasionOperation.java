package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateBarbarianInvasionInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class BarbarianInvasionOperation extends AbstractCardInitiatedNoTargetOperation {

	public BarbarianInvasionOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new InitiateBarbarianInvasionInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use Barbarian Invasion?";
	}

}