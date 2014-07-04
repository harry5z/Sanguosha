package core;

import java.util.ArrayList;

import commands.Command;
import player.PlayerServerSimple;

public interface Game {
	
	/**
	 * start the game
	 */
	public void start();
	
	/**
	 * reset the game
	 */
	public void reset();
	/**
	 * returns a copy of player's info
	 * @return a copy of player's info
	 */
	public ArrayList<PlayerInfo> getPlayers();
	
	/**
	 * Send update to all clients
	 * @param update
	 */
	public void sendToAllClients(Command update);
	
	public void addPlayer(PlayerInfo player);
	
	public PlayerInfo getNextPlayerAlive(PlayerInfo current);

	public PlayerServerSimple findMatch(PlayerInfo p);

	public Deck getDeck();
}
