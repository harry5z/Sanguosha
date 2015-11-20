package commands.game.client;

import net.Connection;
import net.client.ClientUI;
import net.client.GamePanel;
import ui.game.GamePanelUI;

public abstract class GameUIClientCommand implements GameClientCommand {

	private static final long serialVersionUID = 6779995165111144810L;

	@Override
	public final void execute(ClientUI ui, Connection connection) {
		execute((GamePanel) ui.<GamePanelUI>getPanel());
	}
	
	protected abstract void execute(GamePanel panel);

}
