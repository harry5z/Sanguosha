package commands.game.server.ingame;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import cards.Card;
import cards.specials.instant.Chain;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.AbstractSingleStageGameController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.instants.ChainGameController;
import exceptions.server.game.GameFlowInterruptedException;
import exceptions.server.game.IllegalPlayerActionException;
import exceptions.server.game.InvalidCardException;

public class InitiateChainInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Queue<PlayerInfo> targets;
	private Card card;

	public InitiateChainInGameServerCommand(Queue<PlayerInfo> targets, Card card) {
		this.targets = targets;
		this.card = card;
	}

	@Override
	public GameController getGameController() {
		return new AbstractSingleStageGameController() {
			
			@Override
			protected void handleOnce(GameInternal game) throws GameFlowInterruptedException {
				if (targets.isEmpty()) {
					// "Recast"
					game.pushGameController(new ReceiveCardsGameController(source, game.getDeck().drawMany(1)));
				} else {
					Queue<PlayerCompleteServer> queue = new LinkedList<>();
					for (PlayerInfo target : targets) {
						queue.add(game.findPlayer(target));
					}
					game.pushGameController(new ChainGameController(source, queue));
				}
				game.pushGameController(new UseCardOnHandGameController(source, Set.of(card)));
			}
		};
	}

	@Override
	public void validate(GameInternal game) throws IllegalPlayerActionException {
		if (card == null || targets == null) {
			throw new IllegalPlayerActionException("Chain: Card/Targets cannot be null");
		}
		
		try {
			card = (Chain) game.getDeck().getValidatedCard(card);
		} catch (InvalidCardException e) {
			throw new IllegalPlayerActionException("Chain: Card is invalid");
		} catch (ClassCastException e) {
			throw new IllegalPlayerActionException("Chain: Card is not a Chain");
		}
		
		if (!source.getCardsOnHand().contains(card)) {
			throw new IllegalPlayerActionException("Chain: Player does not own the card used");
		}
		
		if (targets.size() > 2) {
			throw new IllegalPlayerActionException("Chain: Too many targets");
		}
		
		PlayerCompleteServer target1 = null;
		if (targets.size() >= 1) {
			target1 = game.findPlayer(List.copyOf(targets).get(0));
			if (target1 == null) {
				throw new IllegalPlayerActionException("Chain: Target not found");
			}
			if (!target1.isAlive()) {
				throw new IllegalPlayerActionException("Chain: Target not alive");
			}
		}
		
		PlayerCompleteServer target2 = null;
		if (targets.size() == 2) {
			target2 = game.findPlayer(List.copyOf(targets).get(1));
			if (target2 == null) {
				throw new IllegalPlayerActionException("Chain: Target not found");
			}
			if (!target2.isAlive()) {
				throw new IllegalPlayerActionException("Chain: Target not alive");
			}
			if (target1.equals(target2)) {
				throw new IllegalPlayerActionException("Chain: Two Chain targets cannot be identical");
			}
		}
	}
	
}
