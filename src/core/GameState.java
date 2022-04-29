package core;

import java.util.List;

import core.player.PlayerCompleteClient;
import core.player.PlayerSimple;

/**
 * 
 * A representation of the client-side Game State, e.g. information about oneself
 * and other players
 * 
 * @author Harry
 *
 */
public interface GameState {

	public List<PlayerSimple> getOtherPlayers();

	public PlayerSimple getPlayer(String name);

	public PlayerCompleteClient getSelf();

	public int getNumberOfPlayers();
	
	public int getNumberOfPlayersAlive();

}
