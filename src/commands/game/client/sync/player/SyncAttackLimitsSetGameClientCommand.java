package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackLimitsSetGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = 111665161579659324L;

	private final int limit;
	
	public SyncAttackLimitsSetGameClientCommand(int limit) {
		this.limit = limit;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		state.getSelf().setAttackLimit(limit);
	}

}
