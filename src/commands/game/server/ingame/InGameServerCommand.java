package commands.game.server.ingame;

import java.util.UUID;

import commands.game.server.GameServerCommand;
import core.server.GameRoom;
import core.server.game.controllers.GameController;
import net.Connection;

public abstract class InGameServerCommand implements GameServerCommand {

	private static final long serialVersionUID = 4744490907869746041L;
	
	private UUID responseID;
	
	/**
	 * Set the response ID for this command so the server may accept it
	 * 
	 * @param id : the response ID received from server
	 */
	public void setResponseID(UUID id) {
		this.responseID = id;
	}
	
	/**
	 * Called by the server to verify response ID
	 * 
	 * @return response ID
	 */
	public UUID getResponseID() {
		return responseID;
	}

	/**
	 * <p>{@inheritDoc}</p>
	 * 
	 * if connection is null, then it's called by the game internally as a default response
	 */
	@Override
	public final void execute(GameRoom room, Connection connection) {
		if (connection == null) {
			room.onDefaultResponseReceived(this);
		} else {
			room.onCommandReceived(this, connection);
		}
	}
	
	public abstract GameController getGameController();
}
