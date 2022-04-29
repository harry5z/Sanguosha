package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.player.PlayerInfo;

public abstract class AbstractSingleTargetOperationGameClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	protected final PlayerInfo target;

	public AbstractSingleTargetOperationGameClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	protected final void execute(GamePanel panel) {
		if (panel.getGameState().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(this.getOperation());
		} else {
			panel.getGameUI().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}
	
	protected abstract Operation getOperation();

}
