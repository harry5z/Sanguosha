package commands.game.client;

import core.client.ClientFrame;
import core.client.GamePanel;
import net.Connection;

public abstract class AbstractGameUIClientCommand implements GameClientCommand {

	private static final long serialVersionUID = 1L;
	
	@Override
	public final void execute(ClientFrame frame, Connection connection) {
		try {
			// A GameUIClientCommand should be sent to a previously set up GamePanel
			// if not, it's an error
			this.execute((GamePanel) frame.getPanel());
		} catch (Exception e) {
			// TODO handle command error
			e.printStackTrace();
			throw new RuntimeException("GameUIClientCommand received while no Game UI available");
		}
	}
	
	protected abstract void execute(GamePanel panel);

}
