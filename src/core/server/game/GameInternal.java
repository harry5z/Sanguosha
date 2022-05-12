package core.server.game;

import java.util.List;
import java.util.function.Predicate;

import core.Deck;
import core.event.game.GameEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.server.ConnectionController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.TurnGameController;
import exceptions.server.game.GameFlowInterruptedException;

public interface GameInternal extends GameEventRegistrar, GameDriver {
	
	public List<PlayerCompleteServer> getPlayersAlive();
	
	public int getNumberOfPlayersAlive();
	
	public PlayerCompleteServer findPlayer(PlayerInfo info);
	
	public PlayerCompleteServer findPlayer(Predicate<PlayerCompleteServer> predicate);
	
	public PlayerCompleteServer getNextPlayerAlive(PlayerCompleteServer current);
	
	public PlayerCompleteServer getCurrentPlayer();
	
	public <T extends GameController> T getNextGameController();
	
	public TurnGameController getTurnController();

	public void popGameController();

	public Deck getDeck();
	
	public <T extends GameEvent> void emit(T event) throws GameFlowInterruptedException;

	public ConnectionController getConnectionController();

}
