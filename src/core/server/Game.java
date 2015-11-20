package core.server;

import java.util.List;
import java.util.function.Predicate;

import core.Deck;
import core.PlayerInfo;
import core.server.game.controllers.GameController;
import player.PlayerComplete;

public interface Game {
	
	/**
	 * start the game
	 */
	public void start();
	
	/**
	 * returns a copy of player's info
	 * @return a copy of player's info
	 */
	public List<PlayerInfo> getPlayersInfo();
	
	public List<PlayerComplete> getPlayers();
	
	public void drawCards(PlayerComplete player, int amount);
	
	public void addPlayer(PlayerInfo player);
	
	public PlayerComplete findPlayer(PlayerInfo info);
	
	public PlayerComplete findPlayer(Predicate<PlayerComplete> predicate);
	
	public PlayerComplete getNextPlayerAlive(PlayerComplete current);
	
	public <T extends GameController> T getGameController();
	
	public void pushGameController(GameController controller);
	
	public void popGameController();

	public Deck getDeck();
}
