package listeners.game.server;

import java.util.Set;

import commands.client.game.sync.player.SyncAttackUsedGameClientCommand;
import commands.client.game.sync.player.SyncAttackUsedSetGameClientCommand;
import commands.client.game.sync.player.SyncPlayerStateGameClientCommand;
import commands.client.game.sync.player.SyncWineUsedSetGameClientCommand;
import core.player.PlayerCompleteServer;
import core.player.PlayerSimple;
import core.player.PlayerState;
import core.server.SyncController;
import listeners.game.PlayerStatusListener;

public class ServerInGamePlayerStatusListener extends ServerInGamePlayerListener implements PlayerStatusListener {

	public ServerInGamePlayerStatusListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}

	@Override
	public void onAttackUsed() {
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedGameClientCommand());
	}

	@Override
	public void onSetAttackUsed(int amount) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedSetGameClientCommand(amount));
	}

	@Override
	public void onSetWineUsed(int amount) {
		controller.sendSyncCommandToPlayer(name, new SyncWineUsedSetGameClientCommand(amount));
	}


	@Override
	public void onPlayerStateUpdated(PlayerState state, int value) {
		controller.sendSyncCommandToPlayer(name, new SyncPlayerStateGameClientCommand(state, value));
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedSetGameClientCommand(self.getAttackUsed()));
		controller.sendSyncCommandToPlayer(name, new SyncWineUsedSetGameClientCommand(self.getWineUsed()));
		for (PlayerState state : self.getPlayerStates()) {
			controller.sendSyncCommandToPlayer(name, new SyncPlayerStateGameClientCommand(state, self.getPlayerState(state)));
		}
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		// nothing to refresh
	}

}
