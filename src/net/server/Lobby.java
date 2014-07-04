package net.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Timer;

import net.Connection;
import utils.Log;
import utils.RoomIDUtil;
import commands.lobby_commands.LobbyDisplayUIClientCommand;

public class Lobby extends ServerEntity {
	private static final String TAG = "Lobby";
	private final List<Room> rooms;
	private Timer timer;

	public Lobby() {
		rooms = new ArrayList<Room>();
		timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Lobby.this.sentToAllClients(new LobbyDisplayUIClientCommand(getRoomsInfo()));
			}
		});
		timer.start();
	}
	
	/**
	 * Attempts to let user enter the room specified by id
	 * 
	 * @param roomID : room to enter
	 * @param connection : user's connection
	 */
	public void proceedToRoom(int roomID, Connection connection) {
		rooms.stream().filter(room -> room.getRoomID() == roomID).forEach(room -> {
			synchronized(connection.accessLock) {
				if (this.connections.contains(connection)) {
					if(room.onReceivedConnection(connection));
						connections.remove(connection);
				}
				else
					Log.e(TAG, "Connection does not exist");
			}
		});
	}
	private List<RoomInfo> getRoomsInfo() {
		return rooms.stream().map(room -> room.getRoomInfo()).collect(Collectors.toList());
	}
	@Override
	public boolean onReceivedConnection(Connection connection) {
		synchronized (connection.accessLock) {
			if (connections.contains(connection)) {
				Log.e(TAG, "Connection already in lobby");
				return false;
			}
			this.connections.add(connection);
			connection.setConnectionListener(this);
			Log.log(TAG, "Client has entered lobby");
			connection.send(new LobbyDisplayUIClientCommand(getRoomsInfo()));
			return true;
		}
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
		Room room = new Room(
			gameConfig == null ? new GameConfig() : gameConfig, 
			roomConfig == null ? new RoomConfig() : roomConfig,
			this
		);
		rooms.add(room);
		this.proceedToRoom(room.getRoomID(), connection);
	}
	
	public void removeRoom(Room room) {
		RoomIDUtil.returnID(room.getRoomID());
		rooms.remove(room);
	}
	
	@Override
	public void onConnectionLost(Connection connection) {
		Log.e(TAG, "Connection is lost...");
		connections.remove(connection);
	}

}
