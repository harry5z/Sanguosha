package core.client.game.operations;

import commands.game.server.ingame.DecisionInGameServerCommand;
import core.client.GamePanel;
import ui.game.interfaces.Activatable;

public class DecisionOperation implements Operation {
	
	private GamePanel panel;
	private final String message;
	
	public DecisionOperation(String message) {
		this.message = message;
	}
	
	@Override
	public void onConfirmed() {
		this.onDeactivated();
		this.panel.getChannel().send(new DecisionInGameServerCommand(true));
	}
	
	@Override
	public void onCanceled() {
		this.onDeactivated();
		this.panel.getChannel().send(new DecisionInGameServerCommand(false));
	}

	@Override
	public void onActivated(GamePanel panel, Activatable source) {
		this.panel = panel;
		panel.getContent().setMessage(this.message);
		panel.getContent().setConfirmEnabled(true);
		panel.getContent().setCancelEnabled(true);
	}
	
	@Override
	public void onDeactivated() {
		this.panel.getContent().setConfirmEnabled(false);
		this.panel.getContent().setCancelEnabled(false);
		this.panel.getContent().clearMessage();
		this.panel.popOperation();
	}

}
