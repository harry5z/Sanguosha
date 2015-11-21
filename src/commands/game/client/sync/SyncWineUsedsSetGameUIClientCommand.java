package commands.game.client.sync;

import commands.game.client.GameUIClientCommand;
import exceptions.server.game.InvalidPlayerCommandException;
import net.client.GamePanel;

public class SyncWineUsedsSetGameUIClientCommand extends GameUIClientCommand {

	private static final long serialVersionUID = -1579729852291363280L;

	private final int amount;
	
	public SyncWineUsedsSetGameUIClientCommand(int amount) {
		this.amount = amount;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getContent().getSelf().setWineUsed(amount);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
