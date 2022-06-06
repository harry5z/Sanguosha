package core.client.game.operations.basics;

import commands.server.ingame.InGameServerCommand;
import commands.server.ingame.UsePeachInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class PeachOperation extends AbstractCardInitiatedNoTargetOperation {

	public PeachOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new UsePeachInGameServerCommand(this.activator.getCard());
	}
	
	@Override
	protected String getMessage() {
		return "Use Peach?";
	}

}
