package commands.game.client.sync.player;

import core.GameState;
import core.player.Player;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncFlipGameClientCommand extends AbstractSyncPlayerClientCommand {

	private static final long serialVersionUID = -2324631715303653440L;

	private final String name;
	private final boolean flipped;
	
	public SyncFlipGameClientCommand(String name, boolean flipped) {
		this.name = name;
		this.flipped = flipped;
	}
	
	@Override
	protected void sync(GameState state) throws InvalidPlayerCommandException {
		Player player = state.getSelf();
		if (player.getName().equals(name) && player.isFlipped() != flipped) {
			player.flip();
		} else {
			state.getPlayer(name).flip();
		}
	}

}
