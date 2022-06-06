package commands.client.game.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedSetGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = -1579729852291363280L;

	private final int amount;
	
	public SyncWineUsedSetGameClientCommand(int amount) {
		this.amount = amount;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		state.getSelf().setWineUsed(amount);
	}

}
