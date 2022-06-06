package core.server;

import java.util.ArrayList;
import java.util.List;

import commands.client.DisplayRoomUIClientCommand;
import commands.client.UpdateRoomUIClientCommand;
import core.server.game.GameConfig;
import net.UserInfo;
import net.server.ServerEntity;
import net.server.util.ServerUtils;
import utils.Log;
import utils.RoomIDUtil;

public class Room implements ServerEntity {
	private static final String TAG = "Room";
	private final Lobby lobby;
	private final List<LoggedInUser> users;
	/**
	 * unique room id
	 */
	private final int id;
	public final GameConfig gameConfig;
	public final RoomConfig roomConfig;
	
	private GameRoom gameRoom;

	public Room(GameConfig config, RoomConfig roomConfig, Lobby lobby) {
		this.lobby = lobby;
		this.id = RoomIDUtil.getAvailableID();
		this.gameConfig = config;
		this.roomConfig = roomConfig;
		this.users = new ArrayList<>();
		this.gameRoom = null;
	}

	public RoomInfo getRoomInfo() {
		return new RoomInfo(id, roomConfig, users.size());
	}
	
	public boolean isInGame() {
		return gameRoom != null;
	}
	
	public synchronized void startGame() {
		if (this.gameRoom != null) {
			return;
		}
		gameRoom = new GameRoom(this, users, gameConfig);
		gameRoom.getGame().start();
	}
	
	public synchronized void onGameEnded(List<LoggedInUser> remainingUsers) {
		if (gameRoom == null) {
			return;
		}
		gameRoom = null;
		users.clear();
		remainingUsers.forEach(user -> {
			users.add(user);
			user.moveToLocation(this);
		});
		ServerUtils.sendCommandToUsers(new DisplayRoomUIClientCommand(getRoomInfo(), getUserInfos()), users);
		lobby.onUpdateRoomInfo(this);
	}
	
	/**
	 * Get the room's unique id
	 * @return room id
	 */
	public int getRoomID() {
		return id;
	}
	
	public int getRoomSize() {
		return users.size();
	}

	@Override
	public synchronized void onUserJoined(LoggedInUser user) {
		if (users.contains(user)) {
			Log.error(TAG, String.format("User \"%s\" is already in Room %d", user.getName(), id));
			return;
		}
		
		users.add(user);
		user.moveToLocation(this);
		Log.log(TAG, String.format("User \"%s\" entered Room %d", user.getName(), id));
		user.send(new DisplayRoomUIClientCommand(getRoomInfo(), getUserInfos()));
		ServerUtils.sendCommandToUsers(new UpdateRoomUIClientCommand(getUserInfos()), users);
	}
	
	private List<UserInfo> getUserInfos() {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		int i = 0;
		for (LoggedInUser user : users) {
			userInfos.add(new UserInfo(user.getName(), i));
			i++;
		}
		return userInfos;
	}
	
	public synchronized void onUserLeft(LoggedInUser user) {
		if (!users.contains(user)) {
			return;
		}
		users.remove(user);
		lobby.onUpdateRoomInfo(this);
		lobby.onUserJoined(user);
		ServerUtils.sendCommandToUsers(new UpdateRoomUIClientCommand(getUserInfos()), users);
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
	public synchronized void onUserDisconnected(LoggedInUser user) {
		Log.error(TAG, String.format("User \"%s\" disconnected", user.getName()));
		users.remove(user);
		OnlineUserManager.get().logout(user);
	}
	
	@Override
	public void onUserRemoved(LoggedInUser user) {
		if (!users.contains(user)) {
			return;
		}
		users.remove(user);
		lobby.onUpdateRoomInfo(this);
	}
	
	@Override
	public void onUserReconnected(LoggedInUser user) {
		// user would not reconnect to room
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
