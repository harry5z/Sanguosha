package commands.client.game.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncResetWineEffectiveGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 373001483127652341L;

	private final String name;
	
	public SyncResetWineEffectiveGameClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().resetWineEffective();
		} else {
			// directly modify UI here because no data record of other player's
			// wine usage is needed
			state.getPlayer(name).resetWineEffective();
		}
	}

}
