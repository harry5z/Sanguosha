package commands.game.client.sync;

import commands.game.client.GameUIClientCommand;
import exceptions.server.game.InvalidPlayerCommandException;
import net.client.GamePanel;

public class SyncAttackUsedGameUIClientCommand extends GameUIClientCommand {

	private static final long serialVersionUID = 7306926860771153864L;

	@Override
	protected void execute(GamePanel panel) {
		try {
			panel.getContent().getSelf().useAttack();
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
