package commands.game.client.sync.status;

import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncWineUsedGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 604587862780017946L;

	private final String name;
	
	public SyncWineUsedGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
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
