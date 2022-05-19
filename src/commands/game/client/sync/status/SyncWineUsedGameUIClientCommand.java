package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 604587862780017946L;

	private final String name;
	
	public SyncWineUsedGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void sync(GamePanel panel) {
		try {
			if (panel.getGameState().getSelf().getName().equals(name)) {
				panel.getGameState().getSelf().useWine();
			} else {
				panel.getGameState().getPlayer(name).useWine();
			}
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
