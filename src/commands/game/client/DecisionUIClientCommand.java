package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.DecisionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class DecisionUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	
	public DecisionUIClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new DecisionOperation());
		} else {
			panel.getContent().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}

}
