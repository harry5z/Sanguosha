package commands.game.client.sync.health;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncHealthCurrentChangedGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = -9022015799166943772L;

	private final String name;
	private final int amount;
	
	public SyncHealthCurrentChangedGameUIClientCommand(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}

	@Override
	protected void sync(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().changeHealthCurrentBy(amount);
		} else {
			panel.getGameState().getPlayer(name).changeHealthCurrentBy(amount);
		}
	}

}
