package core.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.Connection;
import net.server.ServerEntity;
import net.server.util.ServerUtils;
import utils.Log;
import utils.RoomIDUtil;

import commands.lobby.DisplayLobbyUIClientCommand;
import commands.lobby.RemoveRoomLobbyUIClientCommand;
import commands.lobby.UpdateRoomLobbyUIClientCommand;
import core.server.game.GameConfig;

public class Lobby extends ServerEntity {
	private static final String TAG = "Lobby";
	private final List<Room> rooms;
	private WelcomeSession session;

	public Lobby(WelcomeSession session) {
		this.session = session;
		rooms = new ArrayList<Room>();
	}
	
	/**
	 * Current serves only testing purpose
	 * @return
	 */
	public boolean hasRoom() {
		return !rooms.isEmpty();
	}

	/**
	 * Attempts to let user enter the room specified by id
	 * 
	 * @param roomID : room to enter
	 * @param connection : user's connection
	 */
	public void proceedToRoom(int roomID, Connection connection) {
		synchronized (this) {
			rooms.stream().filter(room -> room.getRoomID() == roomID).forEach(room -> {
				if (this.connections.contains(connection)) {
					if(room.onUserJoined(connection)) {
						connections.remove(connection);
						ServerUtils.sendCommandToConnections(new UpdateRoomLobbyUIClientCommand(room.getRoomInfo()), connections);
					}
				}
				else
					Log.error(TAG, "Connection does not exist");
			});
		}
		// TODO handle the case when room does not exist
	}
	
	@Override
	public boolean onUserJoined(Connection connection) {
		synchronized (this) {
			if (connections.contains(connection)) {
				Log.error(TAG, "Connection already in lobby");
				return false;
			}
			this.connections.add(connection);
			connection.setConnectionListener(this);
			Log.log(TAG, "Client has entered lobby");
			connection.send(new DisplayLobbyUIClientCommand(getRoomsInfo()));
			return true;
		}
	}
	
	public void onUpdateRoomInfo(Room room) {
		RoomInfo info = room.getRoomInfo();
		if (info.getOccupancy() == 0) {
			RoomIDUtil.returnID(room.getRoomID());
			rooms.remove(room);
			ServerUtils.sendCommandToConnections(new RemoveRoomLobbyUIClientCommand(info), connections);			
		} else {
			ServerUtils.sendCommandToConnections(new UpdateRoomLobbyUIClientCommand(info), connections);
		}
	}
	
	@Override
	public void onConnectionLeft(Connection connection) {
		synchronized (this) {
			if (!connections.contains(connection)) {
				return;
			}
			connections.remove(connection);
		}
		session.onUserJoined(connection);
	}
	
	/**
	 * Add a new room to the lobby with the following parameters:
	 * 
	 * @param gameConfig : configuration of game, if null then init as default
	 * @param roomConfig : configuration of room, if null then init as default
	 * @param connection : connection that sends the request
	 * 
	 * @see GameConfig#GameConfig()
	 * @see RoomConfig#RoomConfig()
	 */
	public void addRoom(GameConfig gameConfig, RoomConfig roomConfig, Connection connection) {
		synchronized (this) {
			if (!connections.contains(connection)) {
				return;
			}
			Room room = new Room(
				gameConfig == null ? new GameConfig() : gameConfig, 
				roomConfig == null ? new RoomConfig() : roomConfig,
				this
			);
			rooms.add(room);
			this.proceedToRoom(room.getRoomID(), connection);
		}
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		synchronized (this) {
			connections.remove(connection);
			OnlineUserManager.get().logout(connection);
		}
		Log.error(TAG, "Connection is lost. " + message);
	}

	private List<RoomInfo> getRoomsInfo() {
		return rooms.stream().map(room -> room.getRoomInfo()).collect(Collectors.toList());
	}
	
}
