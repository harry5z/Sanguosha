package commands.game.server.ingame;

import cards.Card;
import cards.basics.Peach;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.HealGameController;
import exceptions.server.game.InvalidPlayerCommandException;

public class UsePeachInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9088626402130251064L;
	
	private final Card card;

	public UsePeachInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	public void execute(Game game) {
		PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
		try {
			if (card != null) { 
				if (!(card instanceof Peach)) {
					throw new InvalidPlayerCommandException("card " + card + " is not peach");
				}
				if (!currentPlayer.getCardsOnHand().contains(card)) {
					throw new InvalidPlayerCommandException("card " + card + " is not on current player's hand");
				}
			}
			if (!currentPlayer.isDamaged()) {
				throw new InvalidPlayerCommandException("player is at full health");
			}
			currentPlayer.useCard(card);
		} catch (InvalidPlayerCommandException e) {
			e.printStackTrace();
		}
		game.pushGameController(new HealGameController(currentPlayer.getPlayerInfo(), currentPlayer.getPlayerInfo(), game));
		game.getGameController().proceed();
	}

}
