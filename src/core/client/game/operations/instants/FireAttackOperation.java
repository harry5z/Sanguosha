package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateFireAttackInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;

public class FireAttackOperation extends AbstractSingleTargetCardOperation {

	public FireAttackOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateFireAttackInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void setupTargetSelection() {
		GameUI panelUI = this.panel.getGameUI();
		if (this.panel.getGameState().getSelf().getHandCount() > 1) {
			// can use Fire Attack on self if more than one card on hand
			panelUI.getHeroUI().setActivatable(true);
		}
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			if (other.getPlayer().getHandCount() > 0) {
				other.setActivatable(true);
			}
		}
		panelUI.setCancelEnabled(true);
	}

}
