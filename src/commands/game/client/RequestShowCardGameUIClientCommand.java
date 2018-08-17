package commands.game.client;
import core.client.GamePanel;
import core.client.game.operations.basics.ShowCardReactionOperation;
import core.heroes.Hero;
import core.player.PlayerInfo;

public class RequestShowCardGameUIClientCommand extends AbstractGameUIClientCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	
	public RequestShowCardGameUIClientCommand(PlayerInfo target) {
		this.target = target;
	}

	@Override
	protected void execute(GamePanel<? extends Hero> panel) {
		if (panel.getContent().getSelf().getPlayerInfo().equals(this.target)) {
			panel.pushOperation(new ShowCardReactionOperation());
		} else {
			panel.getContent().getOtherPlayerUI(this.target).showCountdownBar();
		}
	}

}
