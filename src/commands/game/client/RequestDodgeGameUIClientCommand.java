package commands.game.client;

import core.PlayerInfo;
import core.client.game.operations.DodgeOperation;
import net.client.GamePanel;

public class RequestDodgeGameUIClientCommand extends GameUIClientCommand {
	
	private static final long serialVersionUID = -4208580489692790341L;

	private final PlayerInfo target;
	
	public RequestDodgeGameUIClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	public void execute(GamePanel panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(target)) {
			panel.pushOperation(new DodgeOperation(), null);
		} else {
			panel.getContent().getOtherPlayerUI(target).showCountdownBar();
		}
		
	}

}
