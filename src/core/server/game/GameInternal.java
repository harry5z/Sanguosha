package core.server.game;

import java.util.List;
import java.util.function.Predicate;

import core.Deck;
import core.event.game.GameEvent;
import core.player.PlayerCompleteServer;
import core.player.PlayerInfo;
import core.player.Role;
import core.server.SyncController;
import core.server.game.controllers.GameController;
import core.server.game.controllers.mechanics.TurnGameController;
import exceptions.server.game.GameFlowInterruptedException;

public interface GameInternal extends GameEventRegistrar, GameDriver {
	
	public List<PlayerCompleteServer> getPlayersAlive();
	
	public int getNumberOfPlayersAlive();
	
	/**
	 * Get the player represented by a PlayerInfo, or null if not found
	 * @param info
	 * @return player, or null
	 */
	public PlayerCompleteServer findPlayer(PlayerInfo info);
	
	public PlayerCompleteServer findPlayer(Predicate<PlayerCompleteServer> predicate);
	
	public PlayerCompleteServer getNextPlayerAlive(PlayerCompleteServer current);
	
	public PlayerCompleteServer getCurrentPlayer();
	
	public <T extends GameController> T getNextGameController();
	
	public <T extends GameController> T getCurrentGameController();
	
	public TurnGameController getTurnController();

	public void popGameController();

	public Deck getDeck();
	
	public <T extends GameEvent> void emit(T event) throws GameFlowInterruptedException;

	public SyncController getSyncController();
	
	/**
	 * Use only when game ends
	 */
	public void end(List<Role> winners);

}
