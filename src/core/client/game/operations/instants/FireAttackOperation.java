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
	protected void onLoadedCustom() {
		GameUI ui = this.panel.getGameUI();
		if (this.panel.getGameState().getSelf().getHandCount() > 1) {
			// can use Fire Attack on oneself if more than one card on hand
			ui.getHeroUI().setActivatable(true);
		}
		for (PlayerUI other : ui.getOtherPlayersUI()) {
			if (other.getPlayer().getHandCount() > 0) {
				other.setActivatable(true);
			}
		}
	}

	@Override
	protected void onUnloadedCustom() {
		this.panel.getGameUI().getHeroUI().setActivatable(false);
		this.panel.getGameUI().getOtherPlayersUI().forEach(ui -> ui.setActivatable(false));
	}

}
