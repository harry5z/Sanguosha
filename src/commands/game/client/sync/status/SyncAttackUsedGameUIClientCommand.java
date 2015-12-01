package commands.game.client.sync.status;

import commands.game.client.GeneralGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 7306926860771153864L;

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			panel.getContent().getSelf().useAttack();
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
