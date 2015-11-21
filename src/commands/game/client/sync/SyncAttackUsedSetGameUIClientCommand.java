package commands.game.client.sync;

import commands.game.client.GameUIClientCommand;
import exceptions.server.game.InvalidPlayerCommandException;
import net.client.GamePanel;

public class SyncAttackUsedSetGameUIClientCommand extends GameUIClientCommand {
	
	private static final long serialVersionUID = 7479944347797215425L;
	
	private final int amount;
	
	public SyncAttackUsedSetGameUIClientCommand(int amount) {
		this.amount = amount;
	}

	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getContent().getSelf().setAttackUsed(amount);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
