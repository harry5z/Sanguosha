package commands.game.server.ingame;

import java.util.Queue;
import java.util.Set;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.mechanics.ReceiveCardsGameController;
import core.server.game.controllers.mechanics.UseCardOnHandGameController;
import core.server.game.controllers.specials.instants.ChainGameController;

public class InitiateChainInGameServerCommand extends InGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final Queue<PlayerInfo> targets;
	private final Card card;

	public InitiateChainInGameServerCommand(Queue<PlayerInfo> targets, Card card) {
		this.targets = targets;
		this.card = card;
	}

	@Override
	public final void execute(Game game) {
		// TODO specify source as the source may not be the current player
		if (this.targets.isEmpty()) {
			// "Recast"
			game.pushGameController(new ReceiveCardsGameController(game, game.getCurrentPlayer(), game.getDeck().drawMany(1)));
		} else {
			game.pushGameController(new ChainGameController(game.getCurrentPlayer().getPlayerInfo(), game, this.targets));
		}
		if (card != null) {
			game.pushGameController(new UseCardOnHandGameController(game, game.getCurrentPlayer(), Set.of(card)));
		}
	}

}
