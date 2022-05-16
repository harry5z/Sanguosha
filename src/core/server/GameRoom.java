package core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import commands.game.client.GameClientCommand;
import commands.game.server.ingame.InGameServerCommand;
import core.player.PlayerInfo;
import core.server.game.Game;
import core.server.game.GameConfig;
import core.server.game.GameImpl;
import net.Connection;
import net.server.ServerEntity;
import utils.Log;

public class GameRoom extends ServerEntity implements ConnectionController {
	
	private final Room room;
	private final Game game;
	private final Map<String, Connection> connectionMap;
	private final Map<Connection, UUID> allowedResponses;
	
	public GameRoom(Room room, Set<Connection> connections, GameConfig config) {
		this.connections.addAll(connections);
		this.room = room;
		this.connectionMap = new HashMap<>();
		this.allowedResponses = new HashMap<>();
		
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
		UUID allowed = this.allowedResponses.get(connection);
		if (allowed == null || !allowed.equals(command.getResponseID())) {
			Log.error("GameRoom", "Player response UUID mismatch");
			return;
		}
		this.allowedResponses.clear(); // clear all allowed responses once a valid response is received
		game.pushGameController(command.getGameController());
		game.resume();
	}
	
	@Override
	public synchronized void sendCommandToAllPlayers(GameClientCommand command) {
		this.connectionMap.forEach((name, connection) -> {
			this.allowedResponses.put(connection, command.generateResponseID(name));
			connection.send(command);
		});
	}
	
	@Override
	public synchronized void sendCommandToPlayers(Map<String, GameClientCommand> commands) {
		commands.forEach((name, command) -> {
			this.allowedResponses.put(this.connectionMap.get(name), command.generateResponseID(name));
			this.connectionMap.get(name).send(command);
		});
	}
	
	@Override
	public synchronized void sendCommandToPlayer(String name, GameClientCommand command) {
		this.allowedResponses.put(this.connectionMap.get(name), command.generateResponseID(name));
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
