package core.server.game;

import java.util.List;
import java.util.function.Predicate;

import core.Deck;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.game.controllers.GameController;

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
	
	public List<PlayerCompleteServer> getPlayers();
	
	public void drawCards(PlayerCompleteServer player, int amount);
	
	public void addPlayer(PlayerInfo player);
	
	public PlayerCompleteServer findPlayer(PlayerInfo info);
	
	public PlayerCompleteServer findPlayer(Predicate<PlayerCompleteServer> predicate);
	
	public PlayerCompleteServer getNextPlayerAlive(PlayerCompleteServer current);
	
	public <T extends GameController> T getGameController();
	
	public void pushGameController(GameController controller);
	
	public void popGameController();

	public Deck getDeck();
}
