package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.mechanics.HealGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
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
			if (!currentPlayer.isDamaged()) {
				throw new InvalidPlayerCommandException("player is at full health");
			}
			game.pushGameController(new HealGameController(currentPlayer.getPlayerInfo(), currentPlayer.getPlayerInfo(), game));
			if (card != null) {
				game.pushGameController(new UseCardOnHandGameController(game, currentPlayer, Set.of(card)));
			}
		} catch (InvalidPlayerCommandException e) {
			// TODO handle error
			e.printStackTrace();
		}
	}

}
