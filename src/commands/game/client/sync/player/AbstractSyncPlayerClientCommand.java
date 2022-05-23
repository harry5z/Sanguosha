package commands.game.client.sync.player;

import commands.game.client.sync.SyncGameClientCommand;
import core.GameState;
import core.client.ClientFrame;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;

public abstract class AbstractSyncPlayerClientCommand implements SyncGameClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public final void execute(ClientFrame frame, Connection connection) {
		try {
			// A GamePanel should have already been set up. If not, it's an error
			synchronized (frame) {
				this.sync(((GamePanel) frame.getPanel()).getGameState());
			}
		} catch (InvalidPlayerCommandException e) {
			// should not happen
			e.printStackTrace();
		} catch (Exception e) {
			// TODO handle command error
			e.printStackTrace();
			throw new RuntimeException("SyncGameUIClientCommand received while no Game UI available");
		}
	}

	protected abstract void sync(GameState state) throws InvalidPlayerCommandException;

}
