package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackLimitsSetGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = 111665161579659324L;

	private final int limit;
	
	public SyncAttackLimitsSetGameUIClientCommand(int limit) {
		this.limit = limit;
	}

	@Override
	protected void sync(GamePanel panel) {
		try {
			panel.getGameState().getSelf().setAttackLimit(limit);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
