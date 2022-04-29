package commands.game.client.sync.hero;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;

public class SyncHeroGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String owner;
	private final Hero hero;
	
	public SyncHeroGameUIClientCommand(String owner, Hero hero) {
		this.owner = owner;
		this.hero = hero;
	}

	@Override
	protected void execute(GamePanel panel) {
		if (panel.getContent().getSelf().getName().equals(this.owner)) {
			panel.getContent().getSelf().setHero(this.hero);
		} else {
			panel.getContent().getPlayer(this.owner).setHero(this.hero);
		}
	}

}
