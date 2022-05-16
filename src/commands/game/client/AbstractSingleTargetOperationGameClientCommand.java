package commands.game.client;

import java.util.UUID;

import core.client.GamePanel;
import core.client.game.operations.Operation;
import core.player.PlayerInfo;

public abstract class AbstractSingleTargetOperationGameClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	protected final PlayerInfo target;
	private UUID uuid;

	public AbstractSingleTargetOperationGameClientCommand(PlayerInfo target) {
		this.target = target;
		this.uuid = null;
	}

	@Override
	protected final void execute(GamePanel panel) {
		if (shouldClearGamePanel()) {
			panel.getGameState().getSelf().clearDisposalArea();
		}
		if (panel.getGameState().getSelf().getPlayerInfo().equals(this.target)) {
			// response ID must be present for the response to be accepted by server
			panel.setNextResponseID(uuid);
			panel.pushOperation(this.getOperation());
		} else {
			panel.getGameUI().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}
	
	@Override
	public UUID generateResponseID(String name) {
		if (name.equals(this.target.getName())) {
			uuid = UUID.randomUUID();
		}
		return uuid;
	}
	
	protected abstract Operation getOperation();
	
	protected boolean shouldClearGamePanel() {
		return false;
	}

}
