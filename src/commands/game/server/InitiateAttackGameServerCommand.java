package commands.game.server;

import cards.Card;
import core.PlayerInfo;
import core.server.Game;
import core.server.game.controllers.AttackGameController;
import net.Connection;
import net.server.GameRoom;

public class InitiateAttackGameServerCommand implements GameServerCommand {
	
	private static final long serialVersionUID = -4460787768760646177L;

	private final PlayerInfo source;
	private final PlayerInfo target;
	private final Card card;
	
	public InitiateAttackGameServerCommand(PlayerInfo source, PlayerInfo target, Card card) {
		this.source = source;
		this.target = target;
		this.card = card;
	}

	@Override
	public void execute(GameRoom room, Connection connection) {
		Game game = room.getGame();
		game.findPlayer(source).useAttack();
		game.findPlayer(source).useCard(card);
		game.pushGameController(new AttackGameController(source, target, room));
		game.getGameController().proceed();
	}

}
