package commands.game.client.sync.disposal;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncDisposalAreaRefreshGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = 279006683296367016L;

	@Override
	protected void execute(GamePanel panel) {
		panel.getGameState().getSelf().clearDisposalArea();
	}

}
