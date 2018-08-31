package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.DecisionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class DecisionUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final String messsage;
	
	public DecisionUIClientCommand(PlayerInfo target, String message) {
		this.target = target;
		this.messsage = message;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new DecisionOperation(this.messsage));
		} else {
			panel.getContent().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}

}
