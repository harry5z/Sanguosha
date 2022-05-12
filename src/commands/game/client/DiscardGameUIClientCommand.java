package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.mechanics.DiscardOperation;
import core.player.PlayerInfo;

public class DiscardGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final int amount;
	private final PlayerInfo target;

	public DiscardGameUIClientCommand(PlayerInfo target, int amount) {
		this.target = target;
		this.amount = amount;
	}
	
	@Override
	protected void execute(GamePanel panel) {
		panel.getGameState().getSelf().clearDisposalArea();
		if (panel.getGameState().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new DiscardOperation(amount));
		} else {
			panel.getGameUI().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}
	
}
