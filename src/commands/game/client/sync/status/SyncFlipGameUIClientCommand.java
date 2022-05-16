package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import core.player.Player;

public class SyncFlipGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = -2324631715303653440L;

	private final String name;
	private final boolean flipped;
	
	public SyncFlipGameUIClientCommand(String name, boolean flipped) {
		this.name = name;
		this.flipped = flipped;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		Player player = panel.getGameState().getSelf();
		if (player.getName().equals(name) && player.isFlipped() != flipped) {
			player.flip();
		} else {
			panel.getGameUI().getOtherPlayerUI(name).setFlipped(flipped);
		}
	}

}
