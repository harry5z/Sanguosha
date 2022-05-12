package core.server.game;

import java.util.List;
import java.util.function.Predicate;

import core.Deck;
import core.event.game.GameEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.ConnectionController;
import core.server.game.controllers.GameController;
import exceptions.server.game.GameFlowInterruptedException;

public interface GameInternal extends GameEventRegistrar {
	
	public List<PlayerCompleteServer> getPlayers();

	public List<PlayerCompleteServer> getPlayersAlive();
	
	public int getNumberOfPlayersAlive();
	
	public PlayerCompleteServer findPlayer(PlayerInfo info);
	
	public PlayerCompleteServer findPlayer(Predicate<PlayerCompleteServer> predicate);
	
	public PlayerCompleteServer getNextPlayerAlive(PlayerCompleteServer current);
	
	public PlayerCompleteServer getCurrentPlayer();
	
	public void pushGameController(GameController controller);
	
	public <T extends GameController> T getNextGameController();

	public void popGameController();

	public Deck getDeck();
	
	public ConnectionController getConnectionController();

	public <T extends GameEvent> void emit(T event) throws GameFlowInterruptedException;
}
