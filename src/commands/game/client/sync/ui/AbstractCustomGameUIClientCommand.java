package commands.game.client.sync.ui;

import commands.game.client.sync.SyncGameClientCommand;
import core.client.ClientFrame;
import core.client.GamePanel;
import net.Connection;
import ui.game.interfaces.GameUI;

public abstract class AbstractCustomGameUIClientCommand implements SyncGameClientCommand {

	private static final long serialVersionUID = 1L;

	@Override
	public final void execute(ClientFrame frame, Connection connection) {
		try {
			// A GamePanel should have already been set up. If not, it's an error
			synchronized (frame) {
				this.sync(((GamePanel) frame.getPanel()).getGameUI());
			}
		} catch (Exception e) {
			// TODO handle command error
			e.printStackTrace();
			throw new RuntimeException("SyncGameUIClientCommand received while no Game UI available");
		}
	}

	protected abstract void sync(GameUI ui);

}
