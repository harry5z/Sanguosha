package commands.game.server.ingame;

import java.util.Queue;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.ReceiveCardsGameController;
import core.server.game.controllers.specials.instants.ChainGameController;
import exceptions.server.game.InvalidPlayerCommandException;

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
		if (card != null) {		
			try {
				// TODO: convert to controller
				game.getCurrentPlayer().useCard(card);
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
				return;
			}
		}
		
		if (this.targets.isEmpty()) {
			// "Recast"
			game.pushGameController(new ReceiveCardsGameController(game, game.getCurrentPlayer(), game.getDeck().drawMany(1)));
		} else {
			game.pushGameController(new ChainGameController(game.getCurrentPlayer().getPlayerInfo(), game, this.targets));
		}
		game.getGameController().proceed();
	}

}
