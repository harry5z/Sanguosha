package commands.game.client.sync.hero;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;

public class SyncHeroGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String owner;
	private final Hero hero;
	
	public SyncHeroGameUIClientCommand(String owner, Hero hero) {
		this.owner = owner;
		this.hero = hero;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(this.owner)) {
			panel.getGameState().getSelf().setHero(this.hero);
		} else {
			panel.getGameState().getPlayer(this.owner).setHero(this.hero);
		}
	}

}
