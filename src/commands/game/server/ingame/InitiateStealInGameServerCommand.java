package commands.game.server.ingame;

import cards.Card;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import core.server.game.controllers.specials.instants.StealGameController;

public class InitiateStealInGameServerCommand extends AbstractInitiationInGameServerCommand {

	private static final long serialVersionUID = 1L;
	
	public InitiateStealInGameServerCommand(PlayerInfo target, Card card) {
		super(target, card);
	}

	@Override
	protected GameController getController(Game game, PlayerInfo target, GameRoom room) {
		return new StealGameController(game.getCurrentPlayer().getPlayerInfo(), target, room);
	}

}
