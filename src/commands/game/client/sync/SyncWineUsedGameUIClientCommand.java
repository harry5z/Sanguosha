package commands.game.client.sync;

import commands.game.client.GameUIClientCommand;
import exceptions.server.game.InvalidPlayerCommandException;
import net.client.GamePanel;

public class SyncWineUsedGameUIClientCommand extends GameUIClientCommand {

	private static final long serialVersionUID = 604587862780017946L;

	private final String name;
	
	public SyncWineUsedGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			try {
				panel.getContent().getSelf().useWine();
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
			}
		} else {
			panel.getContent().getOtherPlayerUI(name).setWineUsed(true);
		}
	}

}
