package commands.game.client.sync.disposal;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;

public class SyncDisposalAreaRefreshGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = 279006683296367016L;

	@Override
	protected void execute(GamePanel panel) {
		panel.getContent().getSelf().clearDisposalArea();
	}

}
