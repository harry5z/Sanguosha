package listeners.game.server;

import java.util.Set;

import commands.client.game.sync.SyncCommandsUtil;
import commands.client.game.sync.player.SyncDeathGameClientCommand;
import commands.client.game.sync.player.SyncHealthCurrentChangedGameClientCommand;
import commands.client.game.sync.player.SyncHealthCurrentGameClientCommand;
import commands.client.game.sync.player.SyncHealthLimitGameClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
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
				new SyncHealthLimitGameClientCommand(name, limit)
			)
		);
	}

	@Override
	public void onSetHealthCurrent(int current) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthCurrentGameClientCommand(name, current)
			)
		);
	}

	@Override
	public void onHealthChangedBy(int amount) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncHealthCurrentChangedGameClientCommand(name, amount)
			)
		);		
	}

	@Override
	public void onDeath() {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncDeathGameClientCommand(name)
			)
		);	
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		controller.sendSyncCommandToPlayer(name, new SyncHealthLimitGameClientCommand(self.getName(), self.getHealthLimit()));
		controller.sendSyncCommandToPlayer(name, new SyncHealthCurrentGameClientCommand(self.getName(), self.getHealthCurrent()));
		if (!self.isAlive()) {
			controller.sendSyncCommandToPlayer(name, new SyncDeathGameClientCommand(name));
		}
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		controller.sendSyncCommandToPlayer(name, new SyncHealthLimitGameClientCommand(other.getName(), other.getHealthLimit()));
		controller.sendSyncCommandToPlayer(name, new SyncHealthCurrentGameClientCommand(other.getName(), other.getHealthCurrent()));
		if (!other.isAlive()) {
			controller.sendSyncCommandToPlayer(name, new SyncDeathGameClientCommand(other.getName()));
		}
	}

}
