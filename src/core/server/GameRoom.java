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

import commands.game.client.PlayerActionGameClientCommand;
import commands.game.client.sync.SyncGameUIClientCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.GameConfig;
import core.server.game.GameImpl;
import net.Connection;
import net.server.ServerEntity;
import utils.Log;

public class GameRoom extends ServerEntity implements SyncController {
	
	private final Room room;
	private final Game game;
	private final Map<String, Connection> connectionMap;
	private final Map<Connection, UUID> allowedResponseIDs;
	private final Set<Class<? extends InGameServerCommand>> allowedResponseTypes;
	private final Timer timer;
	private TimerTask defaultResponseTask;
	
	public GameRoom(Room room, Set<Connection> connections, GameConfig config) {
		this.connections.addAll(connections);
		this.room = room;
		this.connectionMap = new HashMap<>();
		this.allowedResponseIDs = new HashMap<>();
		this.allowedResponseTypes = new HashSet<>();
		this.timer = new Timer();
		this.defaultResponseTask = null;
		
		// TODO: Fix this when we have actual player info
		// begin ugly part because connection doesn't have unique user information yet
		int i = 0;
		List<PlayerInfo> players = new ArrayList<>();
		for (Connection connection : this.connections) {
			this.connectionMap.put("Player " + i, connection);
			players.add(new PlayerInfo("Player " + i, i));
			i++;
		}
		// end ugly part
		this.game = new GameImpl(this, config, players);

	}
	
	public Game getGame() {
		return game;
	}
	
	public synchronized void onCommandReceived(InGameServerCommand command, Connection connection) {
		UUID allowed = this.allowedResponseIDs.get(connection);
		if (allowed == null || !allowed.equals(command.getResponseID())) {
			Log.error("GameRoom", "Player response UUID mismatch");
			return;
		}
		if (!this.allowedResponseTypes.contains(command.getClass())) {
			Log.error("GameRoom", "Invalid Response Type: " + command.getClass().getSimpleName());
			return;
		}
		this.allowedResponseIDs.remove(connection); // clear UUID once a valid response is received
		if (this.allowedResponseIDs.size() == 0) {
			this.defaultResponseTask.cancel();
			this.defaultResponseTask = null;
		}
		game.pushGameController(command.getGameController());
		game.resume();
	}
	
	public synchronized void onDefaultResponseReceived(InGameServerCommand command) {
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
					command.getDefaultResponse().execute(GameRoom.this, null);
				}
			}
		};
		// add one second to account for potential network delays
		timer.schedule(defaultResponseTask, (room.gameConfig.getGameSpeed() + 1) * 1000);
		
		this.connectionMap.forEach((name, connection) -> {
			UUID id = command.generateResponseID(name);
			if (id != null) {
				this.allowedResponseIDs.put(connection, id);
			}
			command.setResponseTimeoutMS(room.gameConfig.getGameSpeed() * 1000);
			connection.send(command);
		});
	}
	
	@Override
	public synchronized void sendSyncCommandToAllPlayers(SyncGameUIClientCommand command) {
		this.connectionMap.forEach((name, connection) -> {
			connection.send(command);
		});
	}
	
	@Override
	public synchronized void sendSyncCommandToPlayers(Map<String, SyncGameUIClientCommand> commands) {
		commands.forEach((name, command) -> {
			this.connectionMap.get(name).send(command);
		});
	}
	
	@Override
	public synchronized void sendSyncCommandToPlayer(String name, SyncGameUIClientCommand command) {
		this.connectionMap.get(name).send(command);
	}
	
	@Override
	public void onConnectionLost(Connection connection, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onReceivedConnection(Connection connection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onConnectionLeft(Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
