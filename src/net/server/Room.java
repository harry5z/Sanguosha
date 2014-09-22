package net.server;

import commands.room.DisplayRoomUIClientCommand;
import net.Connection;
import utils.Log;
import utils.RoomIDUtil;
import core.Game;

public class Room extends ServerEntity {
	private static final String TAG = "Room";
	private final Object entranceLock = new Object();
	private final Lobby lobby;
	/**
	 * unique room id
	 */
	private final int id;
	private Game game;
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
				connection.send(/* TODO error command */ null);
				return false;
			}
			if (connections.contains(connection)) {
				Log.error(TAG, "Connection already in room");
				return false;
			}
			connections.add(connection);
			connection.setConnectionListener(this);
			Log.log(TAG, "Player entered room " + id);
			connection.send(new DisplayRoomUIClientCommand(this.getRoomInfo()));
			return true;
		}
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

	public void startGame() {
		// this.game = new GameImpl();
		this.game.start();
	}

	public void resetGame() {
		game.reset();
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
