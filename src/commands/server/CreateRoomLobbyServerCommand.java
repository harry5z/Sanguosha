package commands.server;

import core.server.Lobby;
import core.server.LoggedInUser;
import core.server.RoomConfig;
import core.server.game.GameConfig;

/**
 * This Command attempts to create a new room 
 * in the {@linkplain Lobby} for the player
 * 
 * @author Harry
 *
 */
public class CreateRoomLobbyServerCommand extends ServerCommand<Lobby> {
	private static final long serialVersionUID = 1L;
	private final GameConfig game;
	private final RoomConfig room;
	
	/**
	 * Create a room with {@linkplain Lobby#addRoom(GameConfig, RoomConfig)}
	 * 
	 * @param gameConfig : game configuration, nullable
	 * @param roomConfig : room configuration, nullable
	 * 
	 * @see GameConfig#GameConfig()
	 * @see RoomConfig#RoomConfig()
	 */
	public CreateRoomLobbyServerCommand(GameConfig gameConfig, RoomConfig roomConfig) {
		this.game = gameConfig;
		this.room = roomConfig;
	}
	
	@Override
	public void execute(Lobby lobby, LoggedInUser user) {
		lobby.addRoom(game, room, user);
	}

}
