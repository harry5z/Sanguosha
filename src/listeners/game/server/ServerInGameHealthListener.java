package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.health.SyncDeathGameUIClientCommand;
import commands.game.client.sync.health.SyncHealthCurrentChangedGameUIClientCommand;
import commands.game.client.sync.health.SyncHealthCurrentGameUIClientCommand;
import commands.game.client.sync.health.SyncHealthLimitGameUIClientCommand;
import core.server.GameRoom;
import listeners.game.HealthListener;

public class ServerInGameHealthListener extends ServerInGamePlayerListener implements HealthListener {

	public ServerInGameHealthListener(String name, Set<String> allNames, GameRoom room) {
		super(name, allNames, room);
	}
	
	@Override
	public void onSetHealthLimit(int limit) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthLimitGameUIClientCommand(name, limit)
			)
		);
	}

	@Override
	public void onSetHealthCurrent(int current) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthCurrentGameUIClientCommand(name, current)
			)
		);
	}

	@Override
	public void onHealthChangedBy(int amount) {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthCurrentChangedGameUIClientCommand(name, amount)
			)
		);		
	}

	@Override
	public void onDeath() {
		room.sendCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncDeathGameUIClientCommand(name)
			)
		);	
	}

}
