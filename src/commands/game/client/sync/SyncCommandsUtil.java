package commands.game.client.sync;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.GameClientCommand;

public final class SyncCommandsUtil {

	private SyncCommandsUtil() {}
	
	public static Map<String, GameClientCommand> generateMapForSameCommand(
		String name, 
		Set<String> otherNames, 
		GameClientCommand command
	) {
		Map<String, GameClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> command));
		map.put(name, command);
		return map;
	}
	
	public static Map<String, GameClientCommand> generateMapForDifferentCommand(
		String name, 
		Set<String> otherNames, 
		GameClientCommand selfCommand,
		GameClientCommand othersCommand
	) {
		Map<String, GameClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> othersCommand));
		map.put(name, selfCommand);
		return map;
	}
	
}
