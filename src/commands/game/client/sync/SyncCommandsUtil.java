package commands.game.client.sync;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.GameClientCommand;
import core.heroes.Hero;

public final class SyncCommandsUtil {

	private SyncCommandsUtil() {}
	
	public static Map<String, GameClientCommand<? extends Hero>> generateMapForSameCommand(
		String name, 
		Set<String> otherNames, 
		GameClientCommand<? extends Hero> command
	) {
		Map<String, GameClientCommand<? extends Hero>> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> command));
		map.put(name, command);
		return map;
	}
	
	public static Map<String, GameClientCommand<? extends Hero>> generateMapForDifferentCommand(
		String name, 
		Set<String> otherNames, 
		GameClientCommand<? extends Hero> selfCommand,
		GameClientCommand<? extends Hero> othersCommand
	) {
		Map<String, GameClientCommand<? extends Hero>> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> othersCommand));
		map.put(name, selfCommand);
		return map;
	}
	
}
