package commands.game.server.ingame;

import cards.Card;
import commands.game.server.GameServerCommand;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.specials.instants.BarbarianInvasionGameController;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;

public class InitiateBarbarianInvasionInGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo source;
	private final Card card;

	public InitiateBarbarianInvasionInGameServerCommand(PlayerInfo source, Card card) {
		this.source = source;
		this.card = card;
	}


	@Override
	public void execute(GameRoom room, Connection connection) {
		Game game = room.getGame();
		if (card != null) {		
			try {
				game.getCurrentPlayer().useCard(card);
			} catch (InvalidPlayerCommandException e) {
				e.printStackTrace();
				return;
			}
		}
		game.pushGameController(new BarbarianInvasionGameController(source, room));
		game.getGameController().proceed();
	}

}
