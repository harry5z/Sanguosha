package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.basics.AttackReactionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class RequestAttackGameUIClientCommand extends GeneralGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	
	public RequestAttackGameUIClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(target)) {
			panel.pushOperation(new AttackReactionOperation());
		} else {
			panel.getContent().getOtherPlayerUI(target).showCountdownBar();
		}
	}

}
