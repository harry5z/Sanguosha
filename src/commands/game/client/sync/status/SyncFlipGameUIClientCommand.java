package commands.game.client.sync.status;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import core.player.Player;

public class SyncFlipGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = -2324631715303653440L;

	private final String name;
	private final boolean flipped;
	
	public SyncFlipGameUIClientCommand(String name, boolean flipped) {
		this.name = name;
		this.flipped = flipped;
	}
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		Player player = panel.getContent().getSelf();
		if (player.getName().equals(name) && player.isFlipped() != flipped) {
			player.flip();
		} else {
			panel.getContent().getOtherPlayerUI(name).flip(flipped);
		}
	}

}
