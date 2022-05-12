package commands.game.server.ingame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import cards.Card;
import core.event.game.instants.HarvestCardSelectionEvent;
import core.event.handlers.instant.HarvestCardSelectionEventHandler;
import core.player.PlayerCompleteServer;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.HarvestGameController;
import exceptions.server.game.GameFlowInterruptedException;

public class InitiateHarvestInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateHarvestInGameServerCommand(Card card) {
		super(null, card);
	}

	@Override
	protected GameController getInitiationGameController(Game game, PlayerCompleteServer target) {
		// TODO clean up Harvest GameController
		Queue<PlayerCompleteServer> targets = new LinkedList<>();
		PlayerCompleteServer currentPlayer = game.getCurrentPlayer();
		targets.add(currentPlayer);
		PlayerCompleteServer next = game.getNextPlayerAlive(currentPlayer);
		while (next != currentPlayer) {
			targets.add(next);
			next = game.getNextPlayerAlive(next);
		}
		targets.forEach(p -> game.registerEventHandler(new HarvestCardSelectionEventHandler(p)));
		List<Card> cards = game.getDeck().drawMany(targets.size());
		// for client UI update only, won't cause interruption
		try {
			game.emit(new HarvestCardSelectionEvent(null, new HashMap<>(cards.stream().collect(Collectors.toMap(card -> card, card -> false)))));
		} catch (GameFlowInterruptedException e) {
			e.resume();
		}
		return new HarvestGameController(
			game.getCurrentPlayer(),
			targets,
			cards
		);
	}

}
