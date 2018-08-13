package commands.game.server.ingame;

import cards.Card;
import commands.game.server.GameServerCommand;
import core.player.PlayerInfo;
import core.server.GameRoom;
import core.server.game.Game;
import core.server.game.controllers.specials.instants.SabotageGameController;
import exceptions.server.game.InvalidPlayerCommandException;
import net.Connection;

public class InitiateSabotageInGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 1L;
	
	private final PlayerInfo target;
	private final Card card;

	public InitiateSabotageInGameServerCommand(PlayerInfo target, Card card) {
		this.target = target;
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
			new SabotageGameController(
				game.getCurrentPlayer().getPlayerInfo(), 
				this.target,
				room
			)
		);
		game.getGameController().proceed();
	}

}
