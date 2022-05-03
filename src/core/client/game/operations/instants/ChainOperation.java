package core.client.game.operations.instants;

import java.util.LinkedList;
import java.util.Queue;

import commands.game.server.ingame.InGameServerCommand;
import commands.game.server.ingame.InitiateChainInGameServerCommand;
import core.client.game.operations.AbstractMultiTargetCardOperation;
import core.player.PlayerInfo;
import ui.game.interfaces.Activatable;
import ui.game.interfaces.CardUI;
import ui.game.interfaces.GameUI;
import ui.game.interfaces.PlayerUI;

public class ChainOperation extends AbstractMultiTargetCardOperation {

	public ChainOperation(Activatable activator) {
		super(activator, 0, 2);
	}

	@Override
	protected InGameServerCommand getCommand() {
		Queue<PlayerInfo> queue = new LinkedList<>();
		for (PlayerUI player : this.targets) {
			queue.add(player.getPlayer().getPlayerInfo());
		}
		return new InitiateChainInGameServerCommand(queue, ((CardUI) this.activator).getCard());
	}

	@Override
	public void onLoadedCustom() {
		GameUI panelUI = this.panel.getGameUI();
		panelUI.getHeroUI().setActivatable(true);
		for (PlayerUI other : panelUI.getOtherPlayersUI()) {
			other.setActivatable(true);
		}
	}
	
	@Override
	public void onUnloadedCustom() {
		this.panel.getGameUI().getHeroUI().setActivatable(false);
		this.panel.getGameUI().getOtherPlayersUI().forEach(other -> other.setActivatable(false));
	}

}
