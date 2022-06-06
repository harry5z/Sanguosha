package commands.client.game.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedSetGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = 7479944347797215425L;
	
	private final int amount;
	
	public SyncAttackUsedSetGameClientCommand(int amount) {
		this.amount = amount;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		state.getSelf().setAttackUsed(amount);
	}

}
