package commands.game.server.ingame;

import cards.Card;
import commands.game.server.GameServerCommand;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.specials.instants.ArrowSalvoGameController;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;

public class InitiateArrowSalvoInGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 1L;

	private final PlayerInfo source;
	private final Card card;

	public InitiateArrowSalvoInGameServerCommand(PlayerInfo source, Card card) {
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
		game.pushGameController(new ArrowSalvoGameController(source, room));
		game.getGameController().proceed();
	}

}
