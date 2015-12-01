package commands.game.client.sync.status;

import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedsSetGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = -1579729852291363280L;

	private final int amount;
	
	public SyncWineUsedsSetGameUIClientCommand(int amount) {
		this.amount = amount;
	}
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			panel.getContent().getSelf().setWineUsed(amount);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
