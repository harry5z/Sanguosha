package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.StealGameController;

public class InitiateStealInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateStealInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getInitiationGameController(GameInternal game, PlayerCompleteServer target) {
		return new StealGameController(game.getCurrentPlayer(), target);
	}

}
