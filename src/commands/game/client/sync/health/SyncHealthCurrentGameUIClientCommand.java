package commands.game.client.sync.health;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;

public class SyncHealthCurrentGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = 1096072100232629409L;

	private final String name;
	private final int current;
	
	public SyncHealthCurrentGameUIClientCommand(String name, int current) {
		this.name = name;
		this.current = current;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			panel.getContent().getSelf().changeHealthCurrentTo(current);
		} else {
			panel.getContent().getPlayer(name).changeHealthCurrentTo(current);
		}
	}

}
