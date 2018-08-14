package commands.game.server.ingame;

import cards.Card;
import commands.game.server.GameServerCommand;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.GameController;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;

public abstract class AbstractInitiationInGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo target;
	private final Card card;

	public AbstractInitiationInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
		this.card = card;
	}

	@Override
	public final void execute(GameRoom room, Connection connection) {
		Game game = room.getGame();
		if (card != null) {		
			try {
				// TODO: convert to controller
				game.getCurrentPlayer().useCard(card);
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
				return;
			}
		}
		
		game.pushGameController(this.getController(game, this.target, room));
		game.getGameController().proceed();
	}
	
	protected abstract GameController getController(Game game, PlayerInfo target, GameRoom room);

}
