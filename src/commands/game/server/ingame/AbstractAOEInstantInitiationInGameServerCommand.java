package commands.game.server.ingame;

import java.util.LinkedList;
import java.util.Queue;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import exceptions.server.game.IllegalPlayerActionException;

public abstract class AbstractAOEInstantInitiationInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public AbstractAOEInstantInitiationInGameServerCommand(Card card) {
		super(null, card);
	}

	@Override
	protected final GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		Queue<PlayerCompleteServer> queue = new LinkedList<>();
		PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
		PlayerCompleteServer next = game.getNextPlayerAlive(currentPlayer);
		while (next != currentPlayer) {
			queue.add(next);
			next = game.getNextPlayerAlive(next);
		}
		return getAOEInitiationGameController(currentPlayer, queue);
	}
	
	@Override
	protected final void validateTarget(GameInternal game) throws IllegalPlayerActionException {
		// AOE cards have no target
	}
	
	protected abstract GameController getAOEInitiationGameController(PlayerCompleteServer self, Queue<PlayerCompleteServer> others);

}
