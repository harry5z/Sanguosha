package commands.game.server.ingame;

import cards.Card;
import commands.game.server.GameServerCommand;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.specials.instants.CreationInstantSpecialGameController;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;

public class InitiateCreationInGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = -3218714908165332927L;

	private final Card card;

	public InitiateCreationInGameServerCommand(Card card) {
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
		
		game.pushGameController(
			new CreationInstantSpecialGameController(
				game.getCurrentPlayer().getPlayerInfo(), 
				room
			)
		);
		game.getGameController().proceed();
	}

}
