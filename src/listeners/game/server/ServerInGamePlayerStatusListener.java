package listeners.game.server;

import java.util.Set;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.player.SyncAttackUsedGameClientCommand;
import commands.game.client.sync.player.SyncAttackUsedSetGameClientCommand;
import commands.game.client.sync.player.SyncChainGameClientCommand;
import commands.game.client.sync.player.SyncFlipGameClientCommand;
import commands.game.client.sync.player.SyncPlayerStateGameClientCommand;
import commands.game.client.sync.player.SyncWineUsedSetGameClientCommand;
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
	public void onFlip(boolean flipped) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncFlipGameClientCommand(name, flipped)
			)
		);
	}
	
	@Override
	public void onChained(boolean chained) {
		controller.sendSyncCommandToAllPlayers(new SyncChainGameClientCommand(this.name, chained));
	}

	@Override
	public void onPlayerStateUpdated(PlayerState state, int value) {
		controller.sendSyncCommandToPlayer(name, new SyncPlayerStateGameClientCommand(state, value));
	}

	@Override
	public void refreshSelf(PlayerCompleteServer self) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedSetGameClientCommand(self.getAttackUsed()));
		controller.sendSyncCommandToPlayer(name, new SyncWineUsedSetGameClientCommand(self.getWineUsed()));
		controller.sendSyncCommandToPlayer(name, new SyncFlipGameClientCommand(self.getName(), self.isFlipped()));
		controller.sendSyncCommandToPlayer(name, new SyncChainGameClientCommand(self.getName(), self.isChained()));
		for (PlayerState state : self.getPlayerStates()) {
			controller.sendSyncCommandToPlayer(name, new SyncPlayerStateGameClientCommand(state, self.getPlayerState(state)));
		}
	}

	@Override
	public void refreshOther(PlayerSimple other) {
		controller.sendSyncCommandToPlayer(name, new SyncFlipGameClientCommand(other.getName(), other.isFlipped()));
		controller.sendSyncCommandToPlayer(name, new SyncChainGameClientCommand(other.getName(), other.isChained()));
	}

}
