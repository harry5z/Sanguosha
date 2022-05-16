package commands.game.client.sync.status;

import commands.game.client.sync.AbstractSyncGameUIClientCommand;
import core.client.GamePanel;
import core.player.PlayerState;

public class SyncPlayerStateGameUIClientCommand extends AbstractSyncGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerState state;
	private final int value;
	
	public SyncPlayerStateGameUIClientCommand(PlayerState state, int value) {
		this.state = state;
		this.value = value;
	}

	@Override
	protected void execute(GamePanel panel) {
		panel.getGameState().getSelf().updatePlayerState(state, value);
	}

}
