package listeners.game.server;

import java.util.Set;
import java.util.stream.Collectors;

import commands.game.client.sync.SyncCommandsUtil;
import commands.game.client.sync.player.SyncAttackLimitsSetGameClientCommand;
import commands.game.client.sync.player.SyncAttackUsedGameClientCommand;
import commands.game.client.sync.player.SyncAttackUsedSetGameClientCommand;
import commands.game.client.sync.player.SyncChainGameClientCommand;
import commands.game.client.sync.player.SyncFlipGameClientCommand;
import commands.game.client.sync.player.SyncPlayerStateGameClientCommand;
import commands.game.client.sync.player.SyncWineUsedSetGameClientCommand;
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
		controller.sendSyncCommandToPlayer(name, new SyncAttackUsedGameClientCommand(
			targets.stream().map(player -> player.getPlayerInfo()).collect(Collectors.toSet())
		));
	}

	@Override
	public void onSetAttackLimit(int limit) {
		controller.sendSyncCommandToPlayer(name, new SyncAttackLimitsSetGameClientCommand(limit));
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

}
