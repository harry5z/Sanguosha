package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.basics.DodgeReactionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class RequestDodgeGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = -4208580489692790341L;

	private final PlayerInfo target;
	private final String message;
	
	public RequestDodgeGameUIClientCommand(PlayerInfo target, String message) {
		this.target = target;
		this.message = message;
	}

	@Override
	public void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(target)) {
			panel.pushOperation(new DodgeReactionOperation(this.message));
		} else {
			panel.getContent().getOtherPlayerUI(target).showCountdownBar();
		}
	}

}
