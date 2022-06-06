package commands.client.game.sync.ui;

import ui.game.interfaces.GameUI;

public class SyncBattleLogGameUIClientCommand extends AbstractCustomGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final String log;
	
	public SyncBattleLogGameUIClientCommand(String log) {
		this.log = log;
	}

	@Override
	protected void sync(GameUI ui) {
		ui.pushBattleLog(log);
	}

}
