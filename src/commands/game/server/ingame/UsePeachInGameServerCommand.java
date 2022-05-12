package commands.game.server.ingame;

import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.HealGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.InvalidPlayerCommandException;

public class UsePeachInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 9088626402130251064L;
	
	private final Card card;

	public UsePeachInGameServerCommand(Card card) {
		this.card = card;
	}

	@Override
	protected GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
				try {
					if (!currentPlayer.isDamaged()) {
						throw new InvalidPlayerCommandException("player is at full health");
					}
					game.pushGameController(new HealGameController(currentPlayer, currentPlayer));
					if (card != null) {
						game.pushGameController(new UseCardOnHandGameController(currentPlayer, Set.of(card)));
					}
				} catch (InvalidPlayerCommandException e) {
					// TODO handle error
					e.printStackTrace();
				}
			}
		};
	}

}
