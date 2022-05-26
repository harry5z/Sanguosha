package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncHealthCurrentGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = 1096072100232629409L;

	private final String name;
	private final int current;
	
	public SyncHealthCurrentGameClientCommand(String name, int current) {
		this.name = name;
		this.current = current;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().setHealthCurrent(current);
		} else {
			state.getPlayer(name).setHealthCurrent(current);
		}
	}

}
