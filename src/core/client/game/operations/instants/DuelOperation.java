package core.client.game.operations.instants;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateDuelInGameServerCommand;
import core.client.game.operations.AbstractSingleTargetCardOperation;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;

public class DuelOperation extends AbstractSingleTargetCardOperation {

	public DuelOperation(Activatable source) {
		super(source);
	}

	@Override
	protected InGameServerCommand getCommand() {
		return new InitiateDuelInGameServerCommand(this.targetUI.getPlayer().getPlayerInfo(), ((CardUI) this.activator).getCard());
	}

	@Override
	protected void onLoadedCustom() {
		this.panel.getGameUI().getOtherPlayersUI().forEach(other -> other.setActivatable(true));
	}

	@Override
	protected void onUnloadedCustom() {
		this.panel.getGameUI().getOtherPlayersUI().forEach(other -> other.setActivatable(false));
	}

}
