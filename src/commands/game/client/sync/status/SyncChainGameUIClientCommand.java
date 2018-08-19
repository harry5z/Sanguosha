package commands.game.client.sync.status;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.Player;

public class SyncChainGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final boolean chained;
	
	public SyncChainGameUIClientCommand(String name, boolean chained) {
		this.name = name;
		this.chained = chained;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		Player player = panel.getContent().getSelf();
		if (player.getName().equals(this.name)) {
			player.setChained(this.chained);
		} else {
			panel.getContent().getOtherPlayerUI(this.name).setChained(this.chained);
		}
	}

}
