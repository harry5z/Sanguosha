package commands.game.client;

import core.client.GamePanel;
import core.client.game.operations.basics.DodgeReactionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class RequestDodgeGameUIClientCommand extends AbstractGameUIClientCommand {
	
	private static final long serialVersionUID = -4208580489692790341L;

	private final PlayerInfo target;
	
	public RequestDodgeGameUIClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	public void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(target)) {
			panel.pushOperation(new DodgeReactionOperation());
		} else {
			panel.getContent().getOtherPlayerUI(target).showCountdownBar();
		}
	}

}
