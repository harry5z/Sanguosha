package core.server;

import java.util.Map;

import commands.game.client.GameClientCommand;

public interface ConnectionController {
	
	public void sendCommandToAllPlayers(GameClientCommand command);
	
	public void sendCommandToPlayers(Map<String, GameClientCommand> commands);
	
	public void sendCommandToPlayer(String name, GameClientCommand command);
}
