package core;

import java.util.List;

import core.player.PlayerCompleteClient;
import core.player.PlayerSimple;

public interface GameState {

	public List<PlayerSimple> getOtherPlayers();

	public PlayerSimple getPlayer(String name);

	public PlayerCompleteClient getSelf();

	public int getNumberOfPlayers();
	
	public int getNumberOfPlayersAlive();

}
