package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public abstract class AbstractSingleTargetOperationGameClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	protected final PlayerInfo target;

	public AbstractSingleTargetOperationGameClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(this.getOperation());
		} else {
			panel.getContent().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}
	
	protected abstract Operation getOperation();

}
