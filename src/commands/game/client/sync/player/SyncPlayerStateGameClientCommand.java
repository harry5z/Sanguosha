package commands.game.client.sync.player;

import core.GameState;
import core.player.PlayerState;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncPlayerStateGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerState state;
	private final int value;
	
	public SyncPlayerStateGameClientCommand(PlayerState state, int value) {
		this.state = state;
		this.value = value;
	}

	@Override
	protected void sync(GameState game) throws InvalidPlayerCommandException {
		game.getSelf().updatePlayerState(state, value);
	}

}
