package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.server.game.Game;
import core.server.game.controllers.UseCardOnHandGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class UseWineInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 5470029734787854360L;
	
	private final Card wine;

	public UseWineInGameServerCommand(Card card) {
		this.wine = card;
	}

	@Override
	public void execute(Game game) {
		try {
			if (game.getCurrentPlayer().isWineUsed()) {
				throw new InvalidPlayerCommandException("wine is already used");
			}
			game.getCurrentPlayer().useWine();
			if (wine != null) {
				// TODO specify source as the source may not be the current player
				game.pushGameController(new UseCardOnHandGameController(game, game.getCurrentPlayer(), Set.of(wine)));
			}
			game.getGameController().proceed();
		} catch (InvalidPlayerCommandException e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

}
