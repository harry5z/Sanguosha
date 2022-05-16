package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import core.player.Player;

public class SyncChainGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final boolean chained;
	
	public SyncChainGameUIClientCommand(String name, boolean chained) {
		this.name = name;
		this.chained = chained;
	}

	@Override
	protected void execute(GamePanel panel) {
		Player player = panel.getGameState().getSelf();
		if (player.getName().equals(this.name)) {
			player.setChained(this.chained);
		} else {
			panel.getGameUI().getOtherPlayerUI(this.name).setChained(this.chained);
		}
	}

}
