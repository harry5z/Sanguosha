package commands.game.client.sync.status;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedSetGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = -1579729852291363280L;

	private final int amount;
	
	public SyncWineUsedSetGameUIClientCommand(int amount) {
		this.amount = amount;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getGameState().getSelf().setWineUsed(amount);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
