package commands.game.server.ingame;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
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
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				// TODO specify source as the source may not be the current player
				if (targets.isEmpty()) {
					// "Recast"
					game.pushGameController(new ReceiveCardsGameController(game.getCurrentPlayer(), game.getDeck().drawMany(1)));
				} else {
					Queue<PlayerCompleteServer> queue = new LinkedList<>();
					for (PlayerInfo target : targets) {
						queue.add(game.findPlayer(target));
					}
					game.pushGameController(new ChainGameController(game.getCurrentPlayer(), queue));
				}
				if (card != null) {
					game.pushGameController(new UseCardOnHandGameController(game.getCurrentPlayer(), Set.of(card)));
				}
			}
		};
	}
	
}
