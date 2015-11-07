package net.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import commands.game.client.GameClientCommand;
import net.Connection;
import core.Game;
import core.GameImpl;
import core.PlayerInfo;

public class GameRoom extends ServerEntity {
	
	private final Room room;
	private final Game game;
	private Map<String, Connection> connectionMap;
	
	public GameRoom(Room room, Set<Connection> connections, GameConfig config) {
		this.connections.addAll(connections);
		this.room = room;
		this.connectionMap = new HashMap<>();
		
		// begin ugly part because connection doesn't have unique user information yet
		int i = 1;
		List<PlayerInfo> players = new ArrayList<>();
		for (Connection connection : this.connections) {
			this.connectionMap.put("Player " + i, connection);
			players.add(new PlayerInfo("Player " + i, i));
			i++;
		}
		// end ugly part
		this.game = new GameImpl(this, config, players);

	}
	
	public void startGame() {
		game.start();
	}
	
	public void sendCommandToPlayers(Map<String, GameClientCommand> commands) {
		commands.forEach((name, command) -> this.connectionMap.get(name).send(command));
	}
	
	public void sendCommandToPlayer(String name, GameClientCommand command) {
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
