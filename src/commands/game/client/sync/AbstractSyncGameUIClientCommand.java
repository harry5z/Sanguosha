package commands.game.client.sync;

import core.client.ClientFrame;
import core.client.GamePanel;
import net.Connection;

public abstract class AbstractSyncGameUIClientCommand implements SyncGameUIClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public final void execute(ClientFrame frame, Connection connection) {
		try {
			// A GamePanel should have already been set up. If not, it's an error
			this.sync((GamePanel) frame.getPanel());
		} catch (Exception e) {
			// TODO handle command error
			e.printStackTrace();
			throw new RuntimeException("SyncGameUIClientCommand received while no Game UI available");
		}
	}

	protected abstract void sync(GamePanel panel);

}
