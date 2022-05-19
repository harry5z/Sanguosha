package commands.game.client.sync.player;

import core.GameState;
import core.player.Player;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncChainGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final boolean chained;
	
	public SyncChainGameClientCommand(String name, boolean chained) {
		this.name = name;
		this.chained = chained;
	}

	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		Player player = state.getSelf();
		if (player.getName().equals(this.name)) {
			player.setChained(this.chained);
		} else {
			state.getPlayer(name).setChained(chained);
		}
	}

}
