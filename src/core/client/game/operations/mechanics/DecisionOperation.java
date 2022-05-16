package core.client.game.operations.mechanics;

import commands.game.server.ingame.DecisionInGameServerCommand;
import core.client.game.operations.AbstractOperation;

public class DecisionOperation extends AbstractOperation {
	
	private final String message;
	
	public DecisionOperation(String message) {
		this.message = message;
	}
	
	@Override
	public void onConfirmed() {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.sendResponse(new DecisionInGameServerCommand(true));
	}
	
	@Override
	public void onCanceled() {
		this.onUnloaded();
		this.onDeactivated();
		this.panel.sendResponse(new DecisionInGameServerCommand(false));
	}

	@Override
	public void onLoaded() {
		panel.getGameUI().setMessage(this.message);
		panel.getGameUI().setConfirmEnabled(true);
		panel.getGameUI().setCancelEnabled(true);
	}
	
	@Override
	public void onUnloaded() {
		this.panel.getGameUI().clearMessage();
		this.panel.getGameUI().setConfirmEnabled(false);
		this.panel.getGameUI().setCancelEnabled(false);
	}

}
