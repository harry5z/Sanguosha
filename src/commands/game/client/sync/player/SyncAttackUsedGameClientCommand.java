package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 7306926860771153864L;
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		state.getSelf().useAttack();
	}

}
