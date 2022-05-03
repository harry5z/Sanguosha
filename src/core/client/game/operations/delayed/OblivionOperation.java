package core.client.game.operations.delayed;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateOblivionInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;
import utils.DelayedType;

public class OblivionOperation extends AbstractSingleTargetCardOperation {

	public OblivionOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateOblivionInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void onLoadedCustom() {
		GameUI panelUI = this.panel.getGameUI();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (!other.getPlayer().hasDelayedType(DelayedType.OBLIVION)) {
				other.setActivatable(true);
			}
		}		
	}

	@Override
	protected void onUnloadedCustom() {
		this.panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.setActivatable(false));
	}

}
