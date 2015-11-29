package commands.game.server.ingame;

import cards.Card;
import core.server.Game;
import core.server.game.controllers.WineUsableGameController;

public class UseWineInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 5470029734787854360L;
	
	private final Card wine;

	public UseWineInGameServerCommand(Card card) {
		this.wine = card;
	}

	@Override
	public void execute(Game game) {
		game.<WineUsableGameController>getGameController().onWineUsed(wine);
	}

}
