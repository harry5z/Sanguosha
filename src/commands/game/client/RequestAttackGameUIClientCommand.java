package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.basics.AttackReactionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class RequestAttackGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final String message;
	
	public RequestAttackGameUIClientCommand(PlayerInfo target, String message) {
		this.target = target;
		this.message = message;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(target)) {
			panel.pushOperation(new AttackReactionOperation(this.message));
		} else {
			panel.getContent().getOtherPlayerUI(target).showCountdownBar();
		}
	}

}
