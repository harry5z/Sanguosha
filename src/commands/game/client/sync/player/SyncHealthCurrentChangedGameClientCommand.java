package commands.game.client.sync.player;

import core.GameState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncHealthCurrentChangedGameClientCommand extends AbstractSyncPlayerClientCommand {
	
	private static final long serialVersionUID = -9022015799166943772L;

	private final String name;
	private final int amount;
	
	public SyncHealthCurrentChangedGameClientCommand(String name, int amount) {
		this.name = name;
		this.amount = amount;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		if (state.getSelf().getName().equals(name)) {
			state.getSelf().changeHealthCurrentBy(amount);
		} else {
			state.getPlayer(name).changeHealthCurrentBy(amount);
		}
	}

}
