package core.server.game;

import java.util.List;
import java.util.function.Predicate;

import core.Deck;
import core.event.game.GameEvent;
import core.event.handlers.EventHandler;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.ConnectionController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;

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
	
	public List<PlayerCompleteServer> getPlayersAlive();
	
	public int getNumberOfPlayersAlive();

	public void drawCards(PlayerCompleteServer player, int amount);
	
	public void addPlayer(PlayerInfo player);
	
	public PlayerCompleteServer findPlayer(PlayerInfo info);
	
	public PlayerCompleteServer findPlayer(Predicate<PlayerCompleteServer> predicate);
	
	public PlayerCompleteServer getNextPlayerAlive(PlayerCompleteServer current);
	
	public PlayerCompleteServer getCurrentPlayer();
	
	public <T extends GameController> T getGameController();
	
	public void pushGameController(GameController controller);
	
	public void popGameController();
	
	public void resume();

	public Deck getDeck();
	
	public ConnectionController getConnectionController();
	
	public <T extends GameEvent> void registerEventHandler(EventHandler<T> handler);
	
	public <T extends GameEvent> void removeEventHandler(EventHandler<T> handler);
	
	public <T extends GameEvent> void emit(T event) throws GameFlowInterruptedException;

}
