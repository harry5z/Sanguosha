package commands.client.game.sync.player;

import core.GameState;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncHeroGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String owner;
	private final Hero hero;
	
	public SyncHeroGameClientCommand(String owner, Hero hero) {
		this.owner = owner;
		this.hero = hero;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(this.owner)) {
			state.getSelf().setHero(this.hero);
		} else {
			state.getPlayer(this.owner).setHero(this.hero);
		}
	}

}
