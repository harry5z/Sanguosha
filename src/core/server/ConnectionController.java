package core.server;

import java.util.Map;

import commands.game.client.GameClientCommand;
import core.heroes.Hero;

public interface ConnectionController {
	
	public void sendCommandToPlayers(Map<String, GameClientCommand<? extends Hero>> commands);
	
	public void sendCommandToPlayer(String name, GameClientCommand<? extends Hero> command);
}
