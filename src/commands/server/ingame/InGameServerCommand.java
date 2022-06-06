package commands.server.ingame;

import java.util.UUID;

import core.player.PlayerCompleteServer;
import core.server.GameRoom;
import core.server.LoggedInUser;
import core.server.game.GameInternal;
import core.server.game.controllers.GameController;
import exceptions.server.game.IllegalPlayerActionException;

public abstract class InGameServerCommand extends GameServerCommand {

	private static final long serialVersionUID = 4744490907869746041L;
	
	private UUID responseID;
	protected transient PlayerCompleteServer source;
	
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
	 * Called by the server upon receiving this command to set the source player who made this response
	 * 
	 * @param source : the player who sent this response command
	 */
	public void setSource(PlayerCompleteServer source) {
		this.source = source;
	}
	
	/**
	 * Validate the integrity of the command received from client. If any part is malformed 
	 * or the command cannot be executed (e.g. Use a card that the player does not have), 
	 * an {@link IllegalPlayerActionException} is thrown and this player action is not executed
	 * 
	 * @param game
	 * @throws IllegalPlayerActionException
	 */
	public abstract void validate(GameInternal game) throws IllegalPlayerActionException;

	/**
	 * <p>{@inheritDoc}</p>
	 * 
	 * if connection is null, then it's called by the game internally as a default response
	 */
	@Override
	public final void execute(GameRoom room, LoggedInUser user) {
		if (user == null) {
			room.onDefaultResponseReceived(this);
		} else {
			room.onCommandReceived(this, user);
		}
	}
	
	public abstract GameController getGameController();
}
