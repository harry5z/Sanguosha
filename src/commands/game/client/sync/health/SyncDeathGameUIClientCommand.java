package commands.game.client.sync.health;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncDeathGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = -5617576331867784827L;

	private final String name;
	
	public SyncDeathGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void sync(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().kill();
		} else {
			panel.getGameState().getPlayer(name).kill();
		}
	}

}