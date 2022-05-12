package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.mechanics.DealOperation;
import core.player.PlayerInfo;

public class DealStartGameUIClientCommmand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;

	public DealStartGameUIClientCommmand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	protected void execute(GamePanel panel) {
		panel.getGameState().getSelf().clearDisposalArea();
		if (panel.getGameState().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new DealOperation());
		} else {
			panel.getGameUI().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}

}
