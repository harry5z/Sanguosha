package commands.game.client.sync.status;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 604587862780017946L;

	private final String name;
	
	public SyncWineUsedGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getName().equals(name)) {
			try {
				panel.getGameState().getSelf().useWine();
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
			}
		} else {
			// directly modify UI here because there is no record of 
			// other player's wine usage
			panel.getGameUI().getOtherPlayerUI(name).setWineUsed(true);
		}
	}

}
