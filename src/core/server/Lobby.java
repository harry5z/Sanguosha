package core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import commands.client.DisplayErrorMessageClientCommand;
import commands.client.DisplayLobbyUIClientCommand;
import commands.client.RemoveRoomLobbyUIClientCommand;
import commands.client.UpdateRoomLobbyUIClientCommand;
import core.server.game.GameConfig;
import net.server.ServerEntity;
import net.server.util.ServerUtils;
import utils.Log;
import utils.RoomIDUtil;

public class Lobby implements ServerEntity {
	private static final String TAG = "Lobby";
	private final Map<Integer, Room> rooms;
	private final List<LoggedInUser> users;

	public Lobby() {
		rooms = new HashMap<>();
		users = new ArrayList<>();
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
	public synchronized void proceedToRoom(int roomID, LoggedInUser user) {
		if (rooms.containsKey(roomID)) {
			if (users.contains(user)) {
				Room room = rooms.get(roomID);
				if (room.isInGame()) {
					user.send(new DisplayErrorMessageClientCommand(String.format("Room %d is in game", roomID)));
					return;
				}
				
				if (room.getRoomSize() == room.roomConfig.getCapacity()) {
					user.send(new DisplayErrorMessageClientCommand(String.format("Room %d is full", roomID)));
					return;
				}
				
				users.remove(user);
				room.onUserJoined(user);
				ServerUtils.sendCommandToUsers(new UpdateRoomLobbyUIClientCommand(room.getRoomInfo()), users);
			} else {
				Log.error(TAG, String.format("User \"%s\" is not in lobby", user.getName()));
			}
		} else {
			// refresh user room display
			user.send(new DisplayLobbyUIClientCommand(getRoomsInfo()));
		}
	}
	
	@Override
	public synchronized void onUserJoined(LoggedInUser user) {
		if (users.contains(user)) {
			Log.error(TAG, String.format("User \"%s\" is already in lobby", user.getName()));
			return;
		}
		users.add(user);
		user.moveToLocation(this);
		Log.log(TAG, String.format("User \"%s\" has entered the lobby", user.getName()));
		user.send(new DisplayLobbyUIClientCommand(getRoomsInfo()));
	}
	
	@Override
	public void onUserRemoved(LoggedInUser user) {
		// user would not be removed from Lobby
	}
	
	public void onUpdateRoomInfo(Room room) {
		RoomInfo info = room.getRoomInfo();
		if (info.getOccupancy() == 0) {
			RoomIDUtil.returnID(room.getRoomID());
			rooms.remove(room.getRoomID());
			ServerUtils.sendCommandToUsers(new RemoveRoomLobbyUIClientCommand(info), users);			
		} else {
			ServerUtils.sendCommandToUsers(new UpdateRoomLobbyUIClientCommand(info), users);
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
	public synchronized void addRoom(GameConfig gameConfig, RoomConfig roomConfig, LoggedInUser user) {
		if (!users.contains(user)) {
			return;
		}
		Room room = new Room(
			gameConfig == null ? new GameConfig() : gameConfig, 
			roomConfig == null ? new RoomConfig() : roomConfig,
			this
		);
		rooms.put(room.getRoomID(), room);
		proceedToRoom(room.getRoomID(), user);
	}
	
	@Override
	public synchronized void onUserDisconnected(LoggedInUser user) {
		Log.error(TAG, String.format("User \"%s\" disconnected", user.getName()));
		users.remove(user);
		OnlineUserManager.get().logout(user);
	}

	private List<RoomInfo> getRoomsInfo() {
		return rooms.values().stream().map(room -> room.getRoomInfo()).collect(Collectors.toList());
	}

	@Override
	public void onUserReconnected(LoggedInUser user) {
		// user would not reconnect to lobby
	}
	
}
