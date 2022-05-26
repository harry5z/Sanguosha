package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncHealthLimitGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = -6642017853595462196L;

	private final String name;
	private final int limit;
	
	public SyncHealthLimitGameClientCommand(String name, int limit) {
		this.name = name;
		this.limit = limit;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().setHealthLimit(limit);
		} else {
			state.getPlayer(name).setHealthLimit(limit);
		}
	}

}
