package commands.game.server.ingame;

import cards.Card;
import cards.basics.Wine;
import core.server.game.Game;
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
			if (this.wine != null) { 
				if (!(this.wine instanceof Wine)) {
					throw new InvalidPlayerCommandException("card " + this.wine + " is not wine");
				}
				if (!game.getCurrentPlayer().getCardsOnHand().contains(this.wine)) {
					throw new InvalidPlayerCommandException("card " + this.wine + " is not on current player's hand");
				}
			}
			if (game.getCurrentPlayer().isWineUsed()) {
				throw new InvalidPlayerCommandException("wine is already used");
			}
			game.getCurrentPlayer().useCard(this.wine);
			game.getCurrentPlayer().useWine();
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
		game.getGameController().proceed();
	}

}
