package commands.lobby;

import verifiers.Verifier;

import commands.Command;

import net.Connection;
import net.server.GameConfig;
import net.server.Lobby;
import net.server.RoomConfig;

/**
 * This Command attempts to create a new room 
 * in the {@linkplain Lobby} for the player
 * 
 * @author Harry
 *
 */
public class CreateRoomLobbyServerCommand implements LobbyServerCommand {
	private static final long serialVersionUID = -6076120031259035335L;
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
	public void execute(Lobby lobby, Connection connection) {
		lobby.addRoom(game, room, connection);
	}

	@Override
	public Verifier<Command<Lobby>> getVerifier() {
		return command -> {
			CreateRoomLobbyServerCommand.class.
			
		};
	}

}
