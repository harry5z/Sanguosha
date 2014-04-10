package core;

import java.util.ArrayList;

import player.PlayerServerSimple;
import update.Update;

public interface Framework {
	
	/**
	 * returns a copy of player's info
	 * @return a copy of player's info
	 */
	public ArrayList<PlayerInfo> getPlayers();
	
	/**
	 * Send update to all clients
	 * @param update
	 */
	public void sendToAllClients(Update update);
	
	public void addPlayer(PlayerInfo player);
	
	public PlayerInfo getNextPlayerAlive(PlayerInfo current);

	public PlayerServerSimple findMatch(PlayerInfo p);

	public Deck getDeck();
}
