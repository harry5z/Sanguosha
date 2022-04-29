package commands.game.client.sync.health;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;

public class SyncDeathGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = -5617576331867784827L;

	private final String name;
	
	public SyncDeathGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			panel.getContent().getSelf().kill();
		} else {
			panel.getContent().getPlayer(name).kill();
		}
	}

}