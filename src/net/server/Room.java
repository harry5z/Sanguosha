package net.server;

import java.util.ArrayList;
import java.util.List;

import net.Connection;
import net.UserInfo;
import net.server.util.ServerUtils;
import utils.Log;
import utils.RoomIDUtil;

import commands.room.DisplayRoomUIClientCommand;
import commands.room.UpdateRoomUIClientCommand;

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
			room.startGame();
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
	public boolean onReceivedConnection(Connection connection) {
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
			ServerUtils.sendCommandToConnections(new UpdateRoomUIClientCommand(getUserInfos(true)), connections);
			connections.add(connection);
			connection.setConnectionListener(this);
			Log.log(TAG, "Player entered room " + id);
			connection.send(new DisplayRoomUIClientCommand(getRoomInfo(), getUserInfos(false)));
			return true;
		}
	}
	
	/*
	 * This is way too ugly, change later when I have actual users
	 */
	private List<UserInfo> getUserInfos(boolean optional) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		int i = 0;
		for (Connection connection : connections) {
			userInfos.add(new UserInfo("Player " + i, i));
			i++;
		}
		if (optional) {
			userInfos.add(new UserInfo("Player " + i, i));
		}
		return userInfos;
	}
	
	@Override
	public void onConnectionLeft(Connection connection) {
		synchronized (entranceLock) {
			synchronized (connection.accessLock) {
				if (!connections.contains(connection)) {
					return;
				}
				connections.remove(connection);
				lobby.onUpdateRoomInfo(this);
				lobby.onReceivedConnection(connection);
				ServerUtils.sendCommandToConnections(new UpdateRoomUIClientCommand(getUserInfos(false)), connections);
			}
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
			Log.error(TAG, "Connection Lost");
			connections.remove(connection);
			lobby.onUpdateRoomInfo(this);
		}
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
