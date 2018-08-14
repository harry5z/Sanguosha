package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.BrotherhoodGameController;

public class InitiateBrotherhoodInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateBrotherhoodInGameServerCommand(Card card) {
		super(null, card);
	}
	
	@Override
	protected GameController getController(Game game, PlayerInfo target, GameRoom room) {
		return new BrotherhoodGameController(game.getCurrentPlayer().getPlayerInfo(), room);
	}

}
