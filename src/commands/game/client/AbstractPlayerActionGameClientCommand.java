package commands.game.client;

import core.client.ClientFrame;
import core.client.GamePanel;
import net.Connection;

public abstract class AbstractPlayerActionGameClientCommand implements PlayerActionGameClientCommand {

	private static final long serialVersionUID = 1L;
	
	protected int timeoutMS;
	
	@Override
	public final void execute(ClientFrame frame, Connection connection) {
		try {
			// A GameUIClientCommand should be sent to a previously set up GamePanel
			// if not, it's an error
			synchronized (frame.getPanel()) {
				GamePanel panel = (GamePanel) frame.getPanel();
				// Other players' countdown bar is stopped upon new Player Action received.
				panel.getGameUI().getOtherPlayersUI().forEach(playerUI -> playerUI.stopCountdown());
				this.execute(panel);
			}
		} catch (Exception e) {
			// TODO handle command error
			e.printStackTrace();
			throw new RuntimeException("PlayerActionGameClientCommand received while no Game UI available");
		}
	}
	
	protected abstract void execute(GamePanel panel);
	
	@Override
	public final void setResponseTimeoutMS(int timeMS) {
		this.timeoutMS = timeMS;
	}

}