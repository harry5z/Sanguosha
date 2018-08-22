package core.client.game.operations;

import commands.game.server.ingame.DecisionInGameServerCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import ui.game.interfaces.Activatable;

public class DecisionOperation implements Operation {
	
	private GamePanel<? extends Hero> panel;
	
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
	public void onActivated(GamePanel<? extends Hero> panel, Activatable source) {
		this.panel = panel;
		panel.getContent().setConfirmEnabled(true);
		panel.getContent().setCancelEnabled(true);
	}
	
	private void onDeactivated() {
		this.panel.getContent().setConfirmEnabled(false);
		this.panel.getContent().setCancelEnabled(false);
		this.panel.popOperation();
	}

}
