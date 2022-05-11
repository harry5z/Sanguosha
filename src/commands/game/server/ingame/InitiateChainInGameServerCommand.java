package commands.game.server.ingame;

import java.util.Queue;
import java.util.Set;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.instants.ChainGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class InitiateChainInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Queue<PlayerInfo> targets;
	private final Card card;

	public InitiateChainInGameServerCommand(Queue<PlayerInfo> targets, Card card) {
		this.targets = targets;
		this.card = card;
	}

	@Override
	protected GameController getGameController(Game game) {
		return new AbstractSingleStageGameController(game) {
			
			@Override
			protected void handleOnce() throws GameFlowInterruptedException {
				// TODO specify source as the source may not be the current player
				if (targets.isEmpty()) {
					// "Recast"
					game.pushGameController(new ReceiveCardsGameController(game, game.getCurrentPlayer(), game.getDeck().drawMany(1)));
				} else {
					game.pushGameController(new ChainGameController(game.getCurrentPlayer().getPlayerInfo(), game, targets));
				}
				if (card != null) {
					game.pushGameController(new UseCardOnHandGameController(game, game.getCurrentPlayer(), Set.of(card)));
				}
			}
		};
	}
	
}
