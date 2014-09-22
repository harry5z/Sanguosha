package net.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Timer;

import net.Connection;
import net.server.util.ServerUtils;
import utils.Log;
import utils.RoomIDUtil;

import commands.lobby.DisplayLobbyUIClientCommand;
import commands.lobby.RemoveRoomLobbyUIClientCommand;
import commands.lobby.UpdateRoomLobbyUIClientCommand;

public class Lobby extends ServerEntity {
	private static final String TAG = "Lobby";
	private final List<Room> rooms;
	private Timer timer;
	private WelcomeSession session;

	public Lobby(WelcomeSession session) {
		this.session = session;
		rooms = new ArrayList<Room>();
//		timer = new Timer(3000, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ServerUtils.sentCommandToConnections(new LobbyDisplayUIClientCommand(getRoomsInfo()), Lobby.this.connections);
//			}
//		});
//		timer.start();
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
					Log.error(TAG, "Connection does not exist");
			}
			ServerUtils.sendCommandToConnections(new UpdateRoomLobbyUIClientCommand(room.getRoomInfo()), connections);
		});
		
	}
	
	private List<RoomInfo> getRoomsInfo() {
		return rooms.stream().map(room -> room.getRoomInfo()).collect(Collectors.toList());
	}
	
	@Override
	public boolean onReceivedConnection(Connection connection) {
		synchronized (connection.accessLock) {
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
		synchronized (connection.accessLock) {
			if (!connections.contains(connection)) {
				return;
			}
			connections.remove(connection);
			session.onReceivedConnection(connection);
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
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		Log.error(TAG, "Connection is lost. " + message);
		connections.remove(connection);
	}

}
