package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.status.SyncAttackLimitsSetGameUIClientCommand;
import commands.game.client.sync.status.SyncAttackUsedGameUIClientCommand;
import commands.game.client.sync.status.SyncAttackUsedSetGameUIClientCommand;
import commands.game.client.sync.status.SyncChainGameUIClientCommand;
import commands.game.client.sync.status.SyncFlipGameUIClientCommand;
import commands.game.client.sync.status.SyncPlayerStateGameUIClientCommand;
import commands.game.client.sync.status.SyncWineUsedSetGameUIClientCommand;
import core.player.Player;
import core.player.PlayerState;
import core.server.SyncController;
import listeners.game.PlayerStatusListener;

public class ServerInGamePlayerStatusListener extends ServerInGamePlayerListener implements PlayerStatusListener {

	public ServerInGamePlayerStatusListener(String name, Set<String> allNames, SyncController controller) {
		super(name, allNames, controller);
	}

	@Override
	public void onAttackUsed(Set<? extends Player> targets) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedGameUIClientCommand(
			targets.stream().map(player -> player.getPlayerInfo()).collect(Collectors.toSet())
		));
	}

	@Override
	public void onSetAttackLimit(int limit) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackLimitsSetGameUIClientCommand(limit));
	}

	@Override
	public void onSetAttackUsed(int amount) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedSetGameUIClientCommand(amount));
	}

	@Override
	public void onSetWineUsed(int amount) {
		controller.sendSyncCommandToPlayer(name, new SyncWineUsedSetGameUIClientCommand(amount));
	}

	@Override
	public void onFlip(boolean flipped) {
		controller.sendSyncCommandToPlayers(
			SyncCommandsUtil.generateMapForSameCommand(
				name, 
				otherNames, 
				new SyncFlipGameUIClientCommand(name, flipped)
			)
		);
	}
	
	@Override
	public void onChained(boolean chained) {
		controller.sendSyncCommandToAllPlayers(new SyncChainGameUIClientCommand(this.name, chained));
	}

	@Override
	public void onPlayerStateUpdated(PlayerState state, int value) {
		controller.sendSyncCommandToPlayer(name, new SyncPlayerStateGameUIClientCommand(state, value));
	}

}
