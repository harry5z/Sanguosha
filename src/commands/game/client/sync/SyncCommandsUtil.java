package commands.game.client.sync;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class SyncCommandsUtil {

	private SyncCommandsUtil() {}
	
	public static Map<String, SyncGameUIClientCommand> generateMapForSameCommand(
		String name, 
		Set<String> otherNames, 
		SyncGameUIClientCommand command
	) {
		Map<String, SyncGameUIClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> command));
		map.put(name, command);
		return map;
	}
	
	public static Map<String, SyncGameUIClientCommand> generateMapForDifferentCommand(
		String name, 
		Set<String> otherNames, 
		SyncGameUIClientCommand selfCommand,
		SyncGameUIClientCommand othersCommand
	) {
		Map<String, SyncGameUIClientCommand> map = otherNames.stream().collect(Collectors.toMap(n -> n, n -> othersCommand));
		map.put(name, selfCommand);
		return map;
	}
	
}
