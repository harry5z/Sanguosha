package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncResetWineEffectiveGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 373001483127652341L;

	private final String name;
	
	public SyncResetWineEffectiveGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void sync(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().resetWineEffective();
		} else {
			// directly modify UI here because no data record of other player's
			// wine usage is needed
			panel.getGameState().getPlayer(name).resetWineEffective();
		}
	}

}
