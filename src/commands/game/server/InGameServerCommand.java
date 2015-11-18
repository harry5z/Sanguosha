package commands.game.server;

import core.server.Game;
import net.Connection;
import net.server.GameRoom;

public abstract class InGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 4744490907869746041L;

	@Override
	public void execute(GameRoom room, Connection connection) {
		this.execute(room.getGame());
	}
	
	public abstract void execute(Game game);
}
