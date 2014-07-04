package core.server;

import net.server.Server;
import core.PlayerInfo;
import player.PlayerComplete;

public abstract class GraphicsUpdater {
	protected final Server server;
	protected final PlayerInfo info;
	
	public GraphicsUpdater(Server server, PlayerComplete player)
	{
		this.server = server;
		this.info = player.getPlayerInfo();
	}
}
