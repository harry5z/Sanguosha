package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncDisposalAreaRefreshGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = 279006683296367016L;

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		state.getSelf().clearDisposalArea();
	}

}
