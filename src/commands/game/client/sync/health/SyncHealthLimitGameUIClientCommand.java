package commands.game.client.sync.health;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;

public class SyncHealthLimitGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = -6642017853595462196L;

	private final String name;
	private final int limit;
	
	public SyncHealthLimitGameUIClientCommand(String name, int limit) {
		this.name = name;
		this.limit = limit;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			panel.getGameState().getSelf().changeHealthLimitTo(limit);
		} else {
			panel.getGameState().getPlayer(name).changeHealthLimitTo(limit);
		}
	}

}
