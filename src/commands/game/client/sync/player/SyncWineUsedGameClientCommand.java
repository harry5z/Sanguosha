package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 604587862780017946L;

	private final String name;
	
	public SyncWineUsedGameClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().useWine();
		} else {
			state.getPlayer(name).useWine();
		}
	}

}
