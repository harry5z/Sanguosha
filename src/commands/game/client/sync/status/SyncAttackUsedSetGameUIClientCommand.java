package commands.game.client.sync.status;

import commands.game.client.AbstractGameUIClientCommand;
import core.client.GamePanel;
import core.heroes.Hero;
import exceptions.server.game.InvalidPlayerCommandException;

public class SyncAttackUsedSetGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = 7479944347797215425L;
	
	private final int amount;
	
	public SyncAttackUsedSetGameUIClientCommand(int amount) {
		this.amount = amount;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		try {
			panel.getContent().getSelf().setAttackUsed(amount);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
	}

}
