package core.server;

import java.util.Map;

import commands.client.game.sync.SyncGameClientCommand;

public interface SyncController {
	
	public void sendSyncCommandToAllPlayers(SyncGameClientCommand command);
	
	public void sendSyncCommandToPlayers(Map<String, SyncGameClientCommand> commands);
	
	public void sendSyncCommandToPlayer(String name, SyncGameClientCommand command);
}
