package net.server;

import commands.room_commands.RoomDisplayUIClientCommand;
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
	private final RoomInfo info;
	private Game game;
	private RoomListener listener;
	public final GameConfig gameConfig;
	public final RoomConfig roomConfig;

	public Room(GameConfig config, RoomConfig roomConfig, Lobby lobby) {
		this.lobby = lobby;
		this.id = RoomIDUtil.getAvailableID();
		this.info = new RoomInfo(id, roomConfig);
		this.gameConfig = config;
		this.roomConfig = roomConfig;
	}

	public RoomInfo getRoomInfo() {
		info.setOccupancy(connections.size());
		return info;
	}
	
	/**
	 * Get the room's unique id
	 * @return room id
	 */
	public int getRoomID() {
		return id;
	}

	public void registerRoomListener(RoomListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onReceivedConnection(Connection connection) {
		synchronized (entranceLock) {
			if (connections.size() == roomConfig.getCapacity()) {
				connection.send(/* TODO error command */ null);
				return false;
			}
			if (connections.contains(connection)) {
				Log.e(TAG, "Connection already in room");
				return false;
			}
			connections.add(connection);
			connection.setConnectionListener(this);
			Log.log(TAG, "Player entered room " + id);
			connection.send(new RoomDisplayUIClientCommand(getRoomInfo()));
			return true;
		}
	}

	public void addPlayer(Connection thread) {
		connections.add(thread);
		listener.onPlayerAdded(thread);
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
	public void onConnectionLost(Connection connection) {
		synchronized (entranceLock) {
			Log.e(TAG, "Connection Lost");
			connections.remove(connection);
			if (connections.isEmpty()) {
				lobby.removeRoom(this);
			}
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
