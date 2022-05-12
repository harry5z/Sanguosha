package commands.game.server.ingame;

import commands.game.server.GameServerCommand;
import core.server.GameRoom;
import core.server.game.controllers.GameController;
import net.Connection;

public abstract class InGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 4744490907869746041L;

	@Override
	public final void execute(GameRoom room, Connection connection) {
		room.getGame().pushGameController(this.getGameController());
		room.getGame().resume();
	}
	
	protected abstract GameController getGameController();
}
