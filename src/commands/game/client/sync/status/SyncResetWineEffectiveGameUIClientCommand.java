package commands.game.client.sync.status;

import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;

public class SyncResetWineEffectiveGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 373001483127652341L;

	private final String name;
	
	public SyncResetWineEffectiveGameUIClientCommand(String name) {
		this.name = name;
	}
	
	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getName().equals(name)) {
			panel.getContent().getSelf().resetWineEffective();
		} else {
			panel.getContent().getOtherPlayerUI(name).setWineUsed(false);
		}
	}

}
