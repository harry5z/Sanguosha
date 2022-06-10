package core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import commands.client.game.PlayerActionGameClientCommand;
import commands.client.game.sync.SyncGameClientCommand;
import commands.server.ingame.InGameServerCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.GameConfig;
import core.server.game.GameImpl;
import exceptions.server.game.IllegalPlayerActionException;
import net.server.ServerEntity;
import utils.Log;

public class GameRoom implements ServerEntity, SyncController {
	
	private final Room room;
	private final GameImpl game;
	private final List<LoggedInUser> users;
	private final List<LoggedInUser> disconnectedUsers;
	private final Map<LoggedInUser, UUID> allowedResponseIDs;
	private final Set<Class<? extends InGameServerCommand>> allowedResponseTypes;
	private final Timer timer;
	private TimerTask defaultResponseTask;
	
	public GameRoom(Room room, List<LoggedInUser> users, GameConfig config) {
		this.room = room;
		this.users = new ArrayList<>();
		this.disconnectedUsers = new ArrayList<>();
		this.allowedResponseIDs = new HashMap<>();
		this.allowedResponseTypes = new HashSet<>();
		this.timer = new Timer();
		this.defaultResponseTask = null;
		
		List<PlayerInfo> players = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			LoggedInUser user = users.get(i);
			user.moveToLocation(this);
			this.users.add(user);
			players.add(new PlayerInfo(user.getName(), i));
		}
		this.game = new GameImpl(this, config, players);
	}
	
	public Game getGame() {
		return game;
	}
	
	public void endGame() {
		OnlineUserManager.get().onGameEnded(disconnectedUsers);
		timer.cancel();
		room.onGameEnded(users);
	}
	
	public synchronized void onCommandReceived(InGameServerCommand command, LoggedInUser user) {
		UUID allowed = this.allowedResponseIDs.get(user);
		if (allowed == null || !allowed.equals(command.getResponseID())) {
			Log.error("GameRoom", "Player response UUID mismatch");
			return;
		}
		
		if (!this.allowedResponseTypes.contains(command.getClass())) {
			Log.error("GameRoom", "Invalid Response Type: " + command.getClass().getSimpleName());
			return;
		}
		
		PlayerCompleteServer source = game.findPlayer(player -> player.getName().equals(user.getName()));
		if (source == null) {
			Log.error("GameRoom", "Player '" + user.getName() + "' not found");
			return;
		}
		command.setSource(source);
		
		try {
			command.validate(game);
		} catch (IllegalPlayerActionException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		this.allowedResponseIDs.remove(user); // clear UUID once a valid response is received
		if (this.allowedResponseIDs.size() == 0) {
			this.defaultResponseTask.cancel();
			this.defaultResponseTask = null;
		}
		game.pushGameController(command.getGameController());
		game.resume();
	}
	
	public synchronized void onDefaultResponseReceived(InGameServerCommand command) {
		if (!allowedResponseIDs.isEmpty()) {
			PlayerCompleteServer source = game.findPlayer(player -> player.getName().equals(
				allowedResponseIDs.keySet().iterator().next().getName()
			));
			command.setSource(source);
		}
		allowedResponseIDs.clear();
		allowedResponseTypes.clear();
		game.pushGameController(command.getGameController());
		game.resume();
	}
	
	public synchronized void sendActionCommandToAllPlayers(PlayerActionGameClientCommand command) {
		this.allowedResponseTypes.clear();
		this.allowedResponseTypes.addAll(command.getAllowedResponseTypes());
		this.defaultResponseTask = new TimerTask() {
			@Override
			public void run() {
				synchronized (GameRoom.this) {
					// skip if already past the current stage
					if (defaultResponseTask != this) {
						return;
					}
					defaultResponseTask = null;
					command.getDefaultResponse().execute(GameRoom.this, (LoggedInUser) null);
				}
			}
		};
		
		this.users.forEach(user -> {
			UUID id = command.generateResponseID(user.getName());
			if (id != null) {
				this.allowedResponseIDs.put(user, id);
			}
			command.setResponseTimeoutMS(room.gameConfig.getGameSpeed() * 1000);
			user.send(command.clone());
		});
		
		// add one second to account for potential network delays
		timer.schedule(defaultResponseTask, command.getTimeoutMS() + 1000);
	}
	
	@Override
	public synchronized void sendSyncCommandToAllPlayers(SyncGameClientCommand command) {
		this.users.forEach(user -> user.send(command));
	}
	
	@Override
	public synchronized void sendSyncCommandToPlayers(Map<String, SyncGameClientCommand> commands) {
		commands.forEach((name, command) -> {
			users.stream().filter(user -> user.getName().equals(name)).forEach(user -> user.send(command));
		});
	}
	
	@Override
	public synchronized void sendSyncCommandToPlayer(String name, SyncGameClientCommand command) {
		users.stream().filter(user -> user.getName().equals(name)).forEach(user -> user.send(command));
	}
	
	@Override
	public synchronized void onUserDisconnected(LoggedInUser user) {
		users.remove(user);
		OnlineUserManager.get().onInGameUserDisconnected(user);
		disconnectedUsers.add(user);
		allowedResponseIDs.remove(user);
		if (this.users.isEmpty()) {
			endGame();
			return;
		}
	}

	@Override
	public void onUserJoined(LoggedInUser user) {
		// user may not join while in game
	}
	
	@Override
	public void onUserReconnected(LoggedInUser user) {
		// TODO exit if game has ended
		disconnectedUsers.remove(user);
		users.add(user);
		game.refreshForPlayer(user.getName());
	}

	@Override
	public void onUserRemoved(LoggedInUser user) {
		disconnectedUsers.remove(user);
		room.onUserRemoved(user);
	}

}
