package core.client;

import java.util.List;

import core.player.PlayerCompleteClient;
import core.player.PlayerSimple;

public interface ClientGameInfo {

	public List<PlayerSimple> getOtherPlayers();

	public PlayerSimple getPlayer(String name);

	public PlayerCompleteClient getSelf();

	public int getNumberOfPlayers();

}
