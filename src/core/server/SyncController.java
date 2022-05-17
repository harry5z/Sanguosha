package core.server;

import java.util.Map;

import commands.game.client.sync.SyncGameUIClientCommand;

public interface SyncController {
	
	public void sendSyncCommandToAllPlayers(SyncGameUIClientCommand command);
	
	public void sendSyncCommandToPlayers(Map<String, SyncGameUIClientCommand> commands);
	
	public void sendSyncCommandToPlayer(String name, SyncGameUIClientCommand command);
}
