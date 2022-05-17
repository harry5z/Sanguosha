package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.health.SyncDeathGameUIClientCommand;
import commands.game.client.sync.health.SyncHealthCurrentChangedGameUIClientCommand;
import commands.game.client.sync.health.SyncHealthCurrentGameUIClientCommand;
import commands.game.client.sync.health.SyncHealthLimitGameUIClientCommand;
import core.server.SyncController;
import listeners.game.HealthListener;

public class ServerInGameHealthListener extends ServerInGamePlayerListener implements HealthListener {

	public ServerInGameHealthListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}
	
	@Override
	public void onSetHealthLimit(int limit) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthLimitGameUIClientCommand(name, limit)
			)
		);
	}

	@Override
	public void onSetHealthCurrent(int current) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthCurrentGameUIClientCommand(name, current)
			)
		);
	}

	@Override
	public void onHealthChangedBy(int amount) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthCurrentChangedGameUIClientCommand(name, amount)
			)
		);		
	}

	@Override
	public void onDeath() {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncDeathGameUIClientCommand(name)
			)
		);	
	}

}
