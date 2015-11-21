package commands.game.client.sync;

import commands.game.client.GameUIClientCommand;
import exceptions.server.game.InvalidPlayerCommandException;
import net.client.GamePanel;

public class SyncAttackLimitsSetGameUIClientCommand extends GameUIClientCommand {
	
	private static final long serialVersionUID = 111665161579659324L;

	private final int limit;
	
	public SyncAttackLimitsSetGameUIClientCommand(int limit) {
		this.limit = limit;
	}

	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getContent().getSelf().setAttackLimit(limit);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
