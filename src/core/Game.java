package core;

import java.util.List;

public interface Game {
	
	/**
	 * start the game
	 */
	public void start();
	
	/**
	 * returns a copy of player's info
	 * @return a copy of player's info
	 */
	public List<PlayerInfo> getPlayers();
	
	public void addPlayer(PlayerInfo player);
	
	public PlayerInfo getNextPlayerAlive(PlayerInfo current);

	public Deck getDeck();
}
