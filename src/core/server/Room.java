package core.server;

import java.util.ArrayList;
import java.util.List;

import net.Connection;
import net.UserInfo;
import net.server.ServerEntity;
import net.server.util.ServerUtils;
import utils.Log;
import utils.RoomIDUtil;

import commands.room.DisplayRoomUIClientCommand;
import commands.room.UpdateRoomUIClientCommand;
import core.server.game.GameConfig;

public class Room extends ServerEntity {
	private static final String TAG = "Room";
	private final Object entranceLock = new Object();
	private final Lobby lobby;
	/**
	 * unique room id
	 */
	private final int id;
	public final GameConfig gameConfig;
	public final RoomConfig roomConfig;

	public Room(GameConfig config, RoomConfig roomConfig, Lobby lobby) {
		this.lobby = lobby;
		this.id = RoomIDUtil.getAvailableID();
		this.gameConfig = config;
		this.roomConfig = roomConfig;
	}

	public RoomInfo getRoomInfo() {
		return new RoomInfo(id, roomConfig, connections.size());
	}
	
	public void startGame() {
		synchronized (entranceLock) {
			GameRoom room = new GameRoom(this, this.connections, this.gameConfig);
			for (Connection connection : connections) {
				connection.setConnectionListener(room);
			}
			room.getGame().start();
		}
	}
	
	public void onGameEnded() {
		synchronized (entranceLock) {
			for (Connection connection : connections) {
				connection.setConnectionListener(this);
			}
			ServerUtils.sendCommandToConnections(new DisplayRoomUIClientCommand(getRoomInfo(), getUserInfos()), connections);
		}
	}
	
	/**
	 * Get the room's unique id
	 * @return room id
	 */
	public int getRoomID() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return false if room is full, or if room does not exist any more
	 */
	@Override
	public boolean onUserJoined(Connection connection) {
		synchronized (entranceLock) {
			if (connections.size() == roomConfig.getCapacity()) {
				// TODO error command
				// connection.send(null);
				return false;
			}
			if (connections.contains(connection)) {
				Log.error(TAG, "Connection already in room");
				return false;
			}
			connections.add(connection);
			connection.setConnectionListener(this);
			Log.log(TAG, "Player entered room " + id);
			connection.send(new DisplayRoomUIClientCommand(getRoomInfo(), getUserInfos()));
			ServerUtils.sendCommandToConnections(new UpdateRoomUIClientCommand(getUserInfos()), connections);
			return true;
		}
	}
	
	private List<UserInfo> getUserInfos() {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		int i = 0;
		for (Connection connection : connections) {
			userInfos.add(new UserInfo(OnlineUserManager.get().getUser(connection).getName(), i));
			i++;
		}
		return userInfos;
	}
	
	@Override
	public void onConnectionLeft(Connection connection) {
		synchronized (entranceLock) {
			if (!connections.contains(connection)) {
				return;
			}
			connections.remove(connection);
			lobby.onUpdateRoomInfo(this);
			lobby.onUserJoined(connection);
			ServerUtils.sendCommandToConnections(new UpdateRoomUIClientCommand(getUserInfos()), connections);
		}
	}

	/**
	 * This is used to modify the game configuration
	 * 
	 * @return gameConfig : the game configuration
	 */
	public GameConfig getGameConfig() {
		return this.gameConfig;
	}

	@Override
	public void onConnectionLost(Connection connection, String message) {
		synchronized (entranceLock) {
			connections.remove(connection);
			lobby.onUpdateRoomInfo(this);
		}
		Log.error(TAG, "Connection Lost");
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Room))
			return false;
		return id == ((Room) obj).id;
	}

}
