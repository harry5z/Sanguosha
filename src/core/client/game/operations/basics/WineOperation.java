package core.client.game.operations.basics;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.UseWineInGameServerCommand;
import core.client.game.operations.AbstractCardInitiatedNoTargetOperation;
import ui.game.interfaces.Activatable;

public class WineOperation extends AbstractCardInitiatedNoTargetOperation {

	public WineOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommandOnConfirm() {
		return new UseWineInGameServerCommand(this.activator.getCard());
	}

	@Override
	protected String getMessage() {
		return "Use Wine?";
	}
	
}
