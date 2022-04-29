package core.client.game.operations.delayed;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateOblivionInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.ClientGameUI;
import ui.game.interfaces.PlayerUI;
import utils.DelayedType;

public class OblivionOperation extends AbstractSingleTargetCardOperation {

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateOblivionInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void setupTargetSelection() {
		ClientGameUI panelUI = this.panel.getContent();
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (!other.getPlayer().hasDelayedType(DelayedType.OBLIVION)) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
