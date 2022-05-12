package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.CreationGameController;

public class InitiateCreationInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = -1L;

	public InitiateCreationInGameServerCommand(Card card) {
		super(null, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new CreationGameController(game.getCurrentPlayer());
	}

}
