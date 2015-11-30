package core.client;

import java.util.List;

import player.PlayerCompleteClient;
import player.PlayerSimple;

public interface ClientGameInfo {

	public List<PlayerSimple> getOtherPlayers();

	public PlayerSimple getPlayer(String name);

	public PlayerCompleteClient getSelf();

	public int getNumberOfPlayers();

}
