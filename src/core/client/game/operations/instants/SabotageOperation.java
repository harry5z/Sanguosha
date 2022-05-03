package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateSabotageInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;

public class SabotageOperation extends AbstractSingleTargetCardOperation {

	public SabotageOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateSabotageInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void onLoadedCustom() {
		GameUI panelUI = this.panel.getGameUI();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (other.getPlayer().getHandCount() > 0 || other.getPlayer().isEquipped() || !other.getPlayer().getDelayedQueue().isEmpty()) {
				other.setActivatable(true);
			}
		}		
	}

	@Override
	protected void onUnloadedCustom() {
		this.panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.setActivatable(false));
	}

}
