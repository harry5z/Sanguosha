package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncDeathGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = -5617576331867784827L;

	private final String name;
	
	public SyncDeathGameClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().kill();
		} else {
			state.getPlayer(name).kill();
		}
	}

}