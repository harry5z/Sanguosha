package commands.game.client;

import core.client.ClientFrame;
import core.client.GamePanel;
import core.heroes.Hero;
import net.Connection;
import ui.game.interfaces.ClientGameUI;

public abstract class GeneralGameUIClientCommand implements GameClientCommand<Hero> {

	private static final long serialVersionUID = 6779995165111144810L;

	@Override
	public final void execute(ClientFrame ui, Connection connection) {
		execute((GamePanel<Hero>) ui.<ClientGameUI<Hero>>getPanel());
	}
	
	protected abstract void execute(GamePanel<? extends Hero> panel);

}
