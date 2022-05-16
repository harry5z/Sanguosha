package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedSetGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = 7479944347797215425L;
	
	private final int amount;
	
	public SyncAttackUsedSetGameUIClientCommand(int amount) {
		this.amount = amount;
	}

	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getGameState().getSelf().setAttackUsed(amount);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
