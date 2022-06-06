package commands.client.game.sync;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class SyncCommandsUtil {

	private SyncCommandsUtil() {}
	
	public static Map<String, SyncGameClientCommand> generateMapForSameCommand(
		String name, 
		Set<String> otherNames, 
		SyncGameClientCommand command
	) {
		Map<String, SyncGameClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> command));
		map.put(name, command);
		return map;
	}
	
	public static Map<String, SyncGameClientCommand> generateMapForDifferentCommand(
		String name, 
		Set<String> otherNames, 
		SyncGameClientCommand selfCommand,
		SyncGameClientCommand othersCommand
	) {
		Map<String, SyncGameClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> othersCommand));
		map.put(name, selfCommand);
		return map;
	}
	
}
