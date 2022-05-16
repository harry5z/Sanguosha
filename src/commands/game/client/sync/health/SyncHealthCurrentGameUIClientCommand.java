package commands.game.client.sync.health;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;

public class SyncHealthCurrentGameUIClientCommand extends AbstractSyncGameUIClientCommand {
	
	private static final long serialVersionUID = 1096072100232629409L;

	private final String name;
	private final int current;
	
	public SyncHealthCurrentGameUIClientCommand(String name, int current) {
		this.name = name;
		this.current = current;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().changeHealthCurrentTo(current);
		} else {
			panel.getGameState().getPlayer(name).changeHealthCurrentTo(current);
		}
	}

}
