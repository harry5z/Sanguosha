package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.HarvestGameController;

public class InitiateHarvestInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;

	public InitiateHarvestInGameServerCommand(Card card) {
		super(null, card);
	}

	@Override
	protected GameController getController(Game game, PlayerInfo target) {
		return new HarvestGameController(game.getCurrentPlayer().getPlayerInfo(), game);
	}

}
